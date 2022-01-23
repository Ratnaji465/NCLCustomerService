package com.ncl.nclcustomerservice.uploadfiles;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.activity.MainActivity;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;
import com.ncl.nclcustomerservice.object.ContractList;
import com.ncl.nclcustomerservice.object.CustomerList;
import com.ncl.nclcustomerservice.object.GeoTrackingListResVo;
import com.ncl.nclcustomerservice.object.Geo_Tracking_POJO;
import com.ncl.nclcustomerservice.object.MapReqVo;
import com.ncl.nclcustomerservice.object.MastersResVo;
import com.ncl.nclcustomerservice.object.OpportunitiesList;
import com.ncl.nclcustomerservice.object.QuotationList;
import com.ncl.nclcustomerservice.object.SalesOrderList;
import com.ncl.nclcustomerservice.object.Team;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.ncl.nclcustomerservice.uploadfiles.RetryJobReceiver.ACTION_CLEAR;
import static com.ncl.nclcustomerservice.uploadfiles.RetryJobReceiver.ACTION_RETRY;

public class OffLineDataUploadService extends JobIntentService {
    private static final String TAG = "OffLineUploadService";
    RestApiService apiService;
    Disposable mDisposable;
    public static final int NOTIFICATION_ID = 1;
    public static final int NOTIFICATION_RETRY_ID = 2;
    /**
     * Unique job ID for this service.
     */
    private static final int JOB_ID = 103;
    String mFilePath;

    NotificationHelper mNotificationHelper;
    List<String> stringList = Arrays.asList("leads", "customers", "contacts", "sales_call",  "opportunities",
            "contracts", "sales_order", "Quotation", "payment_collection", "ta_da_allowances");
    //List<String> stringList = Arrays.asList("leads", "customers", "contacts");
    private DatabaseHandler db;


    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, OffLineDataUploadService.class, JOB_ID, intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationHelper = new NotificationHelper(this);
        db = DatabaseHandler.getDatabase(this);

    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork: ");


        /**
         * Download/Upload of file
         * The system or framework is already holding a wake lock for us at this point
         */

        // get file file here
        //  mFilePath = intent.getStringExtra("mFilePath");
        if (stringList == null || stringList.size() == 0) {
            Log.e(TAG, "onHandleWork: Invalid file URI");
            return;
        }
        apiService = RetrofitInstance.getApiService();
        Flowable<Object> fileObservable = Flowable.create(new FlowableOnSubscribe<Object>() {
            @Override
            public void subscribe(FlowableEmitter<Object> emitter) throws Exception {
                for (int i = 0; i < stringList.size(); i++) {
                    Log.d("pos: ", "" + i);

                    Team team = new Team();
                    team.teamId = Common.getTeamUserIdFromSP(OffLineDataUploadService.this);
                    team.roleId = String.valueOf(Common.getRoleIdFromSP(OffLineDataUploadService.this));
                    team.tableName = stringList.get(i);
                    final ApiRequestController apiRequestController = new ApiRequestController();
                    apiRequestController.requesterid = Common.getUserIdFromSP(OffLineDataUploadService.this);
                    apiRequestController.requestname = Constants.RequestNames.MASTERS_LIST;
                    apiRequestController.requestparameters = team;

                    String obj1 = new Gson().toJson(apiRequestController);
                    Log.d("JSOn: 1 ", obj1);
                    ApiResponseController abc = apiService.callPost(Constants.API, apiRequestController).blockingGet();
                    Log.d("JSOn: 1 ", abc.toString());
                    updateDB(abc.result);
                    emitter.onNext(i);

                }
                ApiRequestController apiRequestController = callGEOTrackingService();
                ApiResponseController abc = apiService.callPost(Constants.API, apiRequestController).blockingGet();

                updateGeoTable(abc);
                emitter.onNext(13);

                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

        mDisposable = fileObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object progress) throws Exception {
                        // call onProgress()
                        Log.d("progress", "p " + progress.toString());

                        onProgress((int) progress);
                        // if (progress.getCurrentProgress()-progress.getPreviousProgress()>=0.07) {
                        //  Log.d("progress T", "" + progress.toString());
                       /* int progressC = (int) (100 * progress.getCurrentProgress());
                        if (progress.getPreviousProgress() != progressC) {
                            progress.setPreviousProgress(progressC);
                            OffLineDataUploadService.this.onProgress(progressC);
                        }*/

                        // }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // call onErrors() if error occurred during file upload
                      //  OffLineDataUploadService.this.onErrors(throwable);
                        Log.d("Error.", throwable.toString());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        // call onSuccess() while file upload successful
                        Log.d("progress", "Success 100");
                        OffLineDataUploadService.this.onSuccess();


                    }
                });
    }



    private void onErrors(Throwable throwable) {
        /**
         * Error occurred in file uploading
         */
        Intent successIntent = new Intent("com.wave.ACTION_CLEAR_NOTIFICATION");
        successIntent.putExtra("notificationId", NOTIFICATION_ID);
        sendBroadcast(successIntent);


        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,
                0 /* Request code */, new Intent(this, MainActivity.class),
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

    public void updateDB(Object object) {
        if (object != null) {
            MastersResVo mastersResVo = Common.getSpecificDataObject(object, MastersResVo.class);
            if (mastersResVo != null) {
                if (mastersResVo.leadList != null)
                    db.commonDao().insertLeadList(mastersResVo.leadList);
//                if (mastersResVo.complaintList != null)
//                    db.commonDao().insertComplaints(convertComplaintInsertToTable(mastersResVo.complaintList));
                if (mastersResVo.contactList != null)
                    db.commonDao().insertContact(mastersResVo.contactList);

                if (mastersResVo.qutationLists != null) {
                    List<QuotationList> qutationList = mastersResVo.qutationLists;
                    for (int i = 0; i < qutationList.size(); i++) {
                        db.commonDao().insertQuotation(qutationList.get(i));
                        if (qutationList.get(i).qutationProductList != null) {
                            for (int j = 0; j < qutationList.get(i).qutationProductList.size(); j++) {
                                qutationList.get(i).qutationProductList.get(j).quotationLineId = qutationList.get(i).quotationId;
                            }
                            db.commonDao().insertQuotationLineItem(qutationList.get(i).qutationProductList);
                        }
                    }
                }

                if (mastersResVo.contractList != null) {
                    List<ContractList> contractLists = mastersResVo.contractList;
                    for (int i = 0; i < contractLists.size(); i++) {
                        db.commonDao().insertContractList(contractLists.get(i));
                        if (contractLists.get(i).contractProduct != null) {
                            for (int j = 0; j < contractLists.get(i).contractProduct.size(); j++) {
                                contractLists.get(i).contractProduct.get(j).lineItemId = contractLists.get(i).contractId;
                            }
                            db.commonDao().insertContractLineItems(contractLists.get(i).contractProduct);
                        }
                    }
                }

                if (mastersResVo.salesOrderList != null) {
                    db.commonDao().deleteSalesOrderList();
                   // List<SalesOrderList> list = mastersResVo.salesOrderList;

                    List<SalesOrderList> salesOrderLists =mastersResVo.salesOrderList;
//                    salesOrderLists.add(list.get(list.size()-1));
//                    salesOrderLists.add(list.get(list.size()-2));
                    for (int i = 0; i < salesOrderLists.size(); i++) {
                        db.commonDao().insertSalesOrder(salesOrderLists.get(i));
                        if (salesOrderLists.get(i).salesOrderProductList != null) {
                            for (int j = 0; j < salesOrderLists.get(i).salesOrderProductList.size(); j++) {
                                salesOrderLists.get(i).salesOrderProductList.get(j).saleslineItemId = salesOrderLists.get(i).salesOrderId;
                            }
                            db.commonDao().insertSalesOrderLineItem(salesOrderLists.get(i).salesOrderProductList);
                        }

                        if (salesOrderLists.get(i).salesPersonsProducts != null&&salesOrderLists.get(i).salesPersonsProducts.size()>0) {
                            for (int k = 0; k < salesOrderLists.get(i).salesPersonsProducts.size(); k++) {
                                salesOrderLists.get(i).salesPersonsProducts.get(k).saleslineItemId = salesOrderLists.get(i).salesOrderId;
                            }
                            db.commonDao().insertSalesPersonOrderLineItem(salesOrderLists.get(i).salesPersonsProducts);
                        }
                    }
                }
                if (mastersResVo.customerList != null) {
                    List<CustomerList> customerLists = mastersResVo.customerList;
                  /*  for (int i = 0; i < customerLists.size(); i++) {
                        db.commonDao().insertCustomerList(customerLists.get(i));
                        if (customerLists.get(i).customerUserList != null) {
                            for (int j = 0; j < customerLists.get(i).customerUserList.size(); j++) {
                                customerLists.get(i).customerUserList.get(j).lineitemid = customerLists.get(i).customerId;
                            }
                            db.commonDao().insertCustomerLineItems(customerLists.get(i).customerUserList);
                        }
                    }

*/
                    for (int i = 0; i < customerLists.size(); i++) {
                        long key = db.commonDao().insertCustomerList(customerLists.get(i));
                        if (customerLists.get(i).customerUserList!=null) {
                            for (int j = 0; j < customerLists.get(i).customerUserList.size(); j++) {
                                customerLists.get(i).customerUserList.get(j).lineitemid = (int) key;
                            }
                            db.commonDao().insertCustomerLineItems(customerLists.get(i).customerUserList);
                        }
                    }

                }
                if (mastersResVo.opportunitiesList != null) {
                    List<OpportunitiesList> opportunitiesLists = mastersResVo.opportunitiesList;
                    for (int i = 0; i < opportunitiesLists.size(); i++) {
                        db.commonDao().insertOpportunities(opportunitiesLists.get(i));
                        if (opportunitiesLists.get(i).finalProduct != null) {
                            for (int k = 0; k < opportunitiesLists.get(i).finalProduct.size(); k++) {
//                                            opportunitiesLists.get(i).productOpportunitieList.get(k).oppProduct = (int) primaryKey;
                                opportunitiesLists.get(i).finalProduct.get(k).oppProduct = opportunitiesLists.get(i).opportunityId;
                            }
                            db.commonDao().insertOpportunitiesProducts(opportunitiesLists.get(i).finalProduct);
                        }
                        if (opportunitiesLists.get(i).competitionProduct != null) {
                            for (int k = 0; k < opportunitiesLists.get(i).competitionProduct.size(); k++) {
                                opportunitiesLists.get(i).competitionProduct.get(k).opportunityCompetion = opportunitiesLists.get(i).opportunityId;
                            }
                            db.commonDao().insertOpportunityCompetition(opportunitiesLists.get(i).competitionProduct);
                        }

                        if (opportunitiesLists.get(i).brandsProduct != null) {
                            for (int k = 0; k < opportunitiesLists.get(i).brandsProduct.size(); k++) {
                                opportunitiesLists.get(i).brandsProduct.get(k).oppoBrand = opportunitiesLists.get(i).opportunityId;
                            }
                            db.commonDao().insertOpportunityBrandLineItem(opportunitiesLists.get(i).brandsProduct);
                        }

                        if (opportunitiesLists.get(i).associateContact != null){
                            for (int k = 0;k<opportunitiesLists.get(i).associateContact.size();k++){
                                opportunitiesLists.get(i).associateContact.get(k).contactId = String.valueOf(opportunitiesLists.get(i).opportunityId);
                            }
                            // db.commonDao().insertAssociateContacts(opportunitiesLists.get(i).associateContact);
                        }
                    }
                }
                if (mastersResVo.salesCallList != null)
                    db.commonDao().insertSalesCallList(mastersResVo.salesCallList);


                if (mastersResVo.tadaList != null)
                    db.commonDao().insertTadaList(mastersResVo.tadaList);

                if (mastersResVo.paymentCollectionList != null)
                    db.commonDao().insertPaymentCollection(mastersResVo.paymentCollectionList);

            }
        }
    }

    private ApiRequestController callGEOTrackingService() {
        MapReqVo mapReqVo = new MapReqVo();
        mapReqVo.teamId = Common.getTeamUserIdFromSP(this);
        mapReqVo.days = "30";

        final ApiRequestController apiRequestController = new ApiRequestController();
        apiRequestController.requesterid = Common.getUserIdFromSP(OffLineDataUploadService.this);
        apiRequestController.requestname = Constants.RequestNames.GEO_TRACKING_LIST;
        apiRequestController.requestparameters = mapReqVo;

        return apiRequestController;

    }

    private void updateGeoTable(ApiResponseController objectResponse) {
        if (objectResponse.result != null) {
            GeoTrackingListResVo geoTrackingListResVo = Common.getSpecificDataObject(objectResponse.result, GeoTrackingListResVo.class);
            List<Geo_Tracking_POJO> geoTrackingList = geoTrackingListResVo.geoTrackingList;
            if (geoTrackingList != null && geoTrackingList.size() > 0) {
                for (int i = 0; i < geoTrackingList.size(); i++) {
                    geoTrackingList.get(i).visitDate = geoTrackingList.get(i).visitDate.split(" ")[0];
                    db.commonDao().insertGeoTrackingPojo(geoTrackingList.get(i));
                }
            }
        }
    }

}
