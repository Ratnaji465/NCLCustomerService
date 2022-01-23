package com.ncl.nclcustomerservice.uploadfiles;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.JobIntentService;

import androidx.core.app.NotificationCompat;
import android.util.Log;


import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.activity.SplashActivity;

import java.io.File;

import io.reactivex.FlowableEmitter;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.ncl.nclcustomerservice.uploadfiles.RetryJobReceiver.ACTION_CLEAR;
import static com.ncl.nclcustomerservice.uploadfiles.RetryJobReceiver.ACTION_RETRY;


public class FileUploadService extends JobIntentService {
    private static final String TAG = "FileUploadService";
    RestApiService apiService;
    Disposable mDisposable;
    public static final int NOTIFICATION_ID = 1;
    public static final int NOTIFICATION_RETRY_ID = 2;
    /**
     * Unique job ID for this service.
     */
    private static final int JOB_ID = 102;
    String mFilePath;

    NotificationHelper mNotificationHelper;

    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, FileUploadService.class, JOB_ID, intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationHelper = new NotificationHelper(this);

    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork: ");

/*
        *//**
         * Download/Upload of file
         * The system or framework is already holding a wake lock for us at this point
         *//*
        final List<FileDetails> files = FileQueueDatabase.getInstance(this).getRepoDao().getPendingFiles();

        // get file file here
        //  mFilePath = intent.getStringExtra("mFilePath");
        if (files == null || files.size() == 0) {
            Log.e(TAG, "onHandleWork: Invalid file URI");
            return;
        }
        apiService = RetrofitInstance.getApiService();
        Flowable<ProgressUpdate> fileObservable = Flowable.create(new FlowableOnSubscribe<ProgressUpdate>() {
            @Override
            public void subscribe(FlowableEmitter<ProgressUpdate> emitter) throws Exception {
                for (int i = 0; i < files.size(); i++) {
                    Log.d("pos: ", "" + i);
                    if (new File(files.get(i).FilePath).exists())
                        apiService.onFileUpload(FileUploadService.this.createMultipartBody(files.get(i).FilePath, emitter)).blockingGet();
                    FileQueueDatabase.getInstance(FileUploadService.this).getRepoDao().delete(files.get(i));
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

        mDisposable = fileObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProgressUpdate>() {
                    @Override
                    public void accept(ProgressUpdate progress) throws Exception {
                        // call onProgress()
                        Log.d("progress", "" + progress.toString());
                        // if (progress.getCurrentProgress()-progress.getPreviousProgress()>=0.07) {
                        //  Log.d("progress T", "" + progress.toString());
                        int progressC = (int) (100 * progress.getCurrentProgress());
                        if (progress.getPreviousProgress() != progressC) {
                            progress.setPreviousProgress(progressC);
                            FileUploadService.this.onProgress(progressC);
                        }

                        // }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // call onErrors() if error occurred during file upload
                        FileUploadService.this.onErrors(throwable);
                        Log.d("Error.", throwable.toString());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        // call onSuccess() while file upload successful
                        Log.d("progress", "Success 100");
                        FileUploadService.this.onSuccess();

                    }
                });*/
    }

    private void onErrors(Throwable throwable) {
        /**
         * Error occurred in file uploading
         */
        Intent successIntent = new Intent("com.wave.ACTION_CLEAR_NOTIFICATION");
        successIntent.putExtra("notificationId", NOTIFICATION_ID);
        sendBroadcast(successIntent);


        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,
                0 /* Request code */, new Intent(this, SplashActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        /**
         * Add retry action button in notification
         */
        Intent retryIntent = new Intent(this, RetryJobReceiver.class);
        retryIntent.putExtra("notificationId", NOTIFICATION_RETRY_ID);
        retryIntent.putExtra("mFilePath", mFilePath);
        retryIntent.setAction(ACTION_RETRY);

        /**
         * Add clear action button in notification
         */
        Intent clearIntent = new Intent(this, RetryJobReceiver.class);
        clearIntent.putExtra("notificationId", NOTIFICATION_RETRY_ID);
        clearIntent.putExtra("mFilePath", mFilePath);
        clearIntent.setAction(ACTION_CLEAR);

        PendingIntent retryPendingIntent = PendingIntent.getBroadcast(this, 0, retryIntent, 0);
        PendingIntent clearPendingIntent = PendingIntent.getBroadcast(this, 0, clearIntent, 0);
        NotificationCompat.Builder mBuilder = mNotificationHelper.getNotification(getString(R.string.error_upload_failed), getString(R.string.message_upload_failed), resultPendingIntent);
        // attached Retry action in notification
        mBuilder.addAction(android.R.drawable.ic_menu_revert, getString(R.string.btn_retry_not), retryPendingIntent);
        // attached Cancel action in notification
        mBuilder.addAction(android.R.drawable.ic_menu_revert, getString(R.string.btn_cancel_not), clearPendingIntent);
        // Notify notification
        mNotificationHelper.notify(NOTIFICATION_RETRY_ID, mBuilder);
    }

    /**
     * Send Broadcast to FileProgressReceiver with progress
     *
     * @param progress file uploading progress
     */
    private void onProgress(int progress) {
        Intent progressIntent = new Intent(this, FileProgressReceiver.class);
        progressIntent.setAction("com.wave.ACTION_PROGRESS_NOTIFICATION");
        progressIntent.putExtra("notificationId", NOTIFICATION_ID);
        progressIntent.putExtra("progress", progress);
        sendBroadcast(progressIntent);
    }

    /**
     * Send Broadcast to FileProgressReceiver while file upload successful
     */
    private void onSuccess() {
        Intent successIntent = new Intent(this, FileProgressReceiver.class);
        successIntent.setAction("com.wave.ACTION_UPLOADED");
        successIntent.putExtra("notificationId", NOTIFICATION_ID);
        successIntent.putExtra("progress", 100);
        sendBroadcast(successIntent);
    }

    private RequestBody createRequestBodyFromFile(File file, String mimeType) {
        return RequestBody.create(MediaType.parse(mimeType), file);
    }

    private RequestBody createRequestBodyFromText(String mText) {
        return RequestBody.create(MediaType.parse("text/plain"), mText);
    }


    /**
     * return multi part body in format of FlowableEmitter
     *
     * @param filePath
     * @param emitter
     * @return
     */
    private MultipartBody.Part createMultipartBody(String filePath, FlowableEmitter<ProgressUpdate> emitter) {
        File file = new File(filePath);
        return MultipartBody.Part.createFormData("img", file.getName(), createCountingRequestBody(file, "image/jpeg", emitter));
    }

    private RequestBody createCountingRequestBody(File file, String mimeType, final FlowableEmitter<ProgressUpdate> emitter) {
        RequestBody requestBody = createRequestBodyFromFile(file, mimeType);
        return new CountingRequestBody(requestBody, new CountingRequestBody.Listener() {
            private double prevousProgress1 = 0;

            @Override
            public void onRequestProgress(long previousBytesWritten, long bytesWritten, long contentLength, ProgressUpdate progressUpdate) {
                double prevousProgress = (1.0 * previousBytesWritten) / contentLength;
                double progress = (1.0 * bytesWritten) / contentLength;
                /*if (progress - prevousProgress1 > 0.09) {
                    prevousProgress1 = prevousProgress;
                }*/
                progressUpdate.setCurrentProgress(progress);
                emitter.onNext(progressUpdate);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }


}
