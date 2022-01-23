package com.ncl.nclcustomerservice.checkinout;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import android.util.Log;


import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.database.DatabaseHandler;

import java.util.List;
import java.util.Timer;

import static android.content.Context.POWER_SERVICE;

/**
 * Created by suprasoft on 8/10/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    // A reference to the service used to get location updates.
    private LocationUpdatesService mService = null;
    private static final String TAG = AlarmReceiver.class.getSimpleName();
    private static final String CHANNEL_ID = "abc";
    public static final String STARTED_FROM = "JobScheduleService";
    private Messenger mActivityMessenger;
    private static final int NOTIFICATION_ID = 12345678;
    private DatabaseHandler db;
    private SQLiteDatabase sdbw;
    private String geoTrackingId;
    private Timer timer;

    // Tracks the bound state of the service.
    private boolean mBound = false;
    public static final String CUSTOM_INTENT = "com.test.intent.action.ALARM";
    private Context context;
    // Monitors the state of the connection to the service.


    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Log.d("AlarmReceiver", "AlarmReceiver..");
        turnOnScreen(context, 4 * 1000);
        db = DatabaseHandler.getDatabase(context);
      /*  sendMessage(MSG_JOB_START, "Job Started" + "\n Job Id : " +
                                   jobParameters.getJobId());*/

        new JobAsyncTask(context).execute();
        setAlarm(false, context);

    }

    public static void cancelAlarm(Context ctx) {
        AlarmManager alarm = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

        /* cancel any pending alarm */
        alarm.cancel(getPendingIntent(ctx));


    }

    public static void setAlarm(boolean force, Context ctx) {
        cancelAlarm(ctx);
        AlarmManager alarm = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        // EVERY X MINUTES
        long delay = (1000 * 60 * 2);
        long when = System.currentTimeMillis();
        if (!force) {
            when += delay;
        }

        /* fire the broadcast */
        //  alarm.set(AlarmManager.RTC_WAKEUP, when, getPendingIntent(ctx));

        int SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT < Build.VERSION_CODES.KITKAT)
            alarm.set(AlarmManager.RTC_WAKEUP, when, getPendingIntent(ctx));
        else if (Build.VERSION_CODES.KITKAT <= SDK_INT && SDK_INT < Build.VERSION_CODES.M)
            alarm.setExact(AlarmManager.RTC_WAKEUP, when, getPendingIntent(ctx));
        else if (SDK_INT >= Build.VERSION_CODES.M) {
            // alarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, when, getPendingIntent(ctx));
            AlarmManager.AlarmClockInfo ac =
                    new AlarmManager.AlarmClockInfo(when,
                            getPendingIntent(ctx));
            alarm.setAlarmClock(ac, getPendingIntentOpr(ctx));
        }

        ComponentName receiver = new ComponentName(ctx, AlarmReceiver.class);
        PackageManager pm = ctx.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    private static PendingIntent getPendingIntent(Context ctx) {
        // Context ctx;   /* get the application context */
        Intent alarmIntent = new Intent(ctx, AlarmReceiver.class);
        alarmIntent.setAction(CUSTOM_INTENT);

        return PendingIntent.getBroadcast(ctx, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private static PendingIntent getPendingIntentOpr(Context ctx) {
        // Context ctx;   /* get the application context */
        Intent alarmIntent = new Intent(ctx, AlarmReceiver.class);
        alarmIntent.setAction(CUSTOM_INTENT);

        return PendingIntent.getBroadcast(ctx, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void restartService(final Context context) {

        Log.d("restartService", "restartService..");
        freeMemory();
        Intent intent = new Intent(context, LocationUpdatesService.class);
        intent.putExtra("from", "1");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startService(intent);


            // setButtonsState(Utils.requestingLocationUpdates(this));

            // Bind to the service. If the service is in foreground mode, this signals to the service
            // that since this activity is in the foreground, the service can exit foreground mode.
   /* context.bindService(new Intent(context, LocationUpdatesService.class), mServiceConnection,
            Context.BIND_AUTO_CREATE);*/

   /* new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            if (mService!=null)
                mService.requestLocationUpdates();
        }
    }, 5000);
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            if (mService!=null)
               context.unbindService(mServiceConnection);
        }
    }, 10000);*/


        }
    }


    public static boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void freeMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
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


    private class JobAsyncTask extends AsyncTask<Void, Void, Void> {

        private final Context context;

        JobAsyncTask(Context context) {
            Log.d(TAG, "TimerAsyncTask");
            this.context = context;

        }


        @Override
        protected Void doInBackground(Void... voids) {

            Log.d(TAG, "Alarm AsyncTask Running: " + System.currentTimeMillis());
            if (Common.isUserCheckedIn(context) && !serviceIsRunningInForeground(context) ) {
                Log.d(TAG, "Services stared from Timer: ");
                turnOnScreen(context, 10 * 1000);
                Intent serviceIntent = new Intent(context, LocationUpdatesService.class);
                serviceIntent.putExtra(STARTED_FROM, true);
                context.startService(serviceIntent);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void jobParameters) {
            Log.d(TAG, "TimerAsyncTask completed");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Runnable: " + System.currentTimeMillis());
                    if (!Common.isUserCheckedIn(context)) {
                        cancelAlarm(context);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            JobScheduler scheduler = (JobScheduler) context.getSystemService(
                                    Context.JOB_SCHEDULER_SERVICE);
                            List<JobInfo> allPendingJobs = scheduler.getAllPendingJobs();
                            for (JobInfo info : allPendingJobs) {
                                int id = info.getId();
                                scheduler.cancel(id);
                            }
                        }
                    }

                }
            }, 5000);
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
            if (/*getClass().getName()*/LocationUpdatesService.class.getName().equals(service.service.getClassName())) {
                //if (service.foreground || service.started) {
                return true;
                // }
            }
        }
        return false;
    }

}
