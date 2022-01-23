package com.ncl.nclcustomerservice.checkinout;

import android.app.ActivityManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.os.RemoteException;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.Log;


import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.database.DatabaseHandler;

import java.util.Timer;
import java.util.TimerTask;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobScheduleService extends JobService {
    private static final String TAG = JobScheduleService.class.getSimpleName();
    private static final String CHANNEL_ID = "abc";
    public static final String STARTED_FROM = "JobScheduleService";
    private Messenger mActivityMessenger;
    private static final int NOTIFICATION_ID = 12345678;
    private DatabaseHandler db;
    private SQLiteDatabase sdbw;
    private String geoTrackingId;
    private Timer timer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        //    mActivityMessenger = intent.getParcelableExtra(STARTED_FROM);
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        Log.d(TAG, "onStartJob");
        db = DatabaseHandler.getDatabase(this);
      /*  sendMessage(MSG_JOB_START, "Job Started" + "\n Job Id : " +
                                   jobParameters.getJobId());*/

        new JobAsyncTask(this).execute(jobParameters);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "onStopJob");
        //   Toast.makeText(this, getString(R.string.job_stop), Toast.LENGTH_SHORT).show();

        //  sendMessage(MSG_JOB_STOP, getString(R.string.job_stop));
        if (timer != null) {
            timer.cancel();
        }
        return false;
    }

    private class JobAsyncTask extends AsyncTask<JobParameters, Void, JobParameters> {
        private final JobService jobService;

        JobAsyncTask(JobService jobService) {
            Log.d(TAG, "JobAsyncTask");
            this.jobService = jobService;
        }

        @Override
        protected JobParameters doInBackground(JobParameters... jobParameters) {
            //  SystemClock.sleep(5000);
            return jobParameters[0];
        }

        @Override
        protected void onPostExecute(JobParameters jobParameters) {
            Log.d(TAG, "JobAsyncTask completed");


            // Finish the job service if required
//            jobService.jobFinished(jobParameters, false);

            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Log.d(TAG, "JobAsyncTask Running: " + System.currentTimeMillis() + "\n" + Common.isUserCheckedIn(JobScheduleService.this) + "\n" + serviceIsRunningInForeground(JobScheduleService.this) + "\n" + LocationUpdatesService.isPaused(JobScheduleService.this));
                    if (Common.isUserCheckedIn(JobScheduleService.this) && !serviceIsRunningInForeground(JobScheduleService.this)) {
                        Log.d(TAG, "Services stared from job: ");
                        turnOnScreen(JobScheduleService.this, 10 * 1000);
                        Intent serviceIntent = new Intent(JobScheduleService.this, LocationUpdatesService.class);
                        serviceIntent.putExtra(STARTED_FROM, true);
                        startService(serviceIntent);
                    } else {
                        //  stopSelf();
                    }

                }
            }, 2 * 1000, 60 * 1000);
        }
    }

    private void turnOnScreen(Context context, long time) {
        PowerManager.WakeLock screenLock = null;
        if ((context.getSystemService(POWER_SERVICE)) != null) {
            screenLock = ((PowerManager) context.getSystemService(POWER_SERVICE)).newWakeLock(
                    PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, ":Supra_NCL_TAG");
            screenLock.acquire(time /*10 minutes*/);


            //  screenLock.release();
        }
    }

    private void sendMessage(int messageID, @Nullable Object params) {
        if (mActivityMessenger == null) {
            Log.d(TAG, "Service is bound, not started. There's no callback to send a message to.");
            return;
        }
        Message m = Message.obtain();
        m.what = messageID;
        m.obj = params;
        try {
            mActivityMessenger.send(m);
        } catch (RemoteException e) {
            Log.e(TAG, "Error passing service object back to activity.");
        }
    }



    public boolean serviceIsRunningInForeground(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
                Integer.MAX_VALUE)) {
            if (LocationUpdatesService.class.getName().equals(service.service.getClassName())) {
                //if (service.foreground || service.started) {
                return true;
                // }
            }
        }
        return false;
    }


}
