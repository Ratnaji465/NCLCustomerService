package com.ncl.nclcustomerservice.checkinout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.PowerManager;


import com.ncl.nclcustomerservice.commonutils.Common;

import java.util.Calendar;
import java.util.List;

import static android.content.Context.POWER_SERVICE;

/**
 * Created by suprasoft on 10/23/2018.
 */

public class CheckoutAlarmReceiver extends BroadcastReceiver {

    static String CUSTOM_INTENT = "com.nsl.intent.action.ALARM";

    @Override
    public void onReceive(Context context, Intent intent) {
        Common.Log.i("CheckoutAlarmReceiver: ");

        turnOnScreen(context);

        Intent intent1 = new Intent(context, CheckoutService.class);
        context.startService(intent1);
        try {
            cancelAlarm(context);
            AlarmReceiver.cancelAlarm(context);
            context.stopService(new Intent(context, LocationUpdatesService.class));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                JobScheduler scheduler = (JobScheduler) context.getSystemService(
                        Context.JOB_SCHEDULER_SERVICE);
                List<JobInfo> allPendingJobs = scheduler.getAllPendingJobs();
                for (JobInfo info : allPendingJobs) {
                    int id = info.getId();
                    scheduler.cancel(id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void cancelAlarm(Context ctx) {
        AlarmManager alarm = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

        /* cancel any pending alarm */
        alarm.cancel(getPendingIntent(ctx));


    }

    public static void setAlarm(boolean force, Context ctx) {
        cancelAlarm(ctx);
        AlarmManager alarm = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 55);
        calendar.set(Calendar.SECOND, 0);

        // EVERY X MINUTES
        //   long delay = (1000 * 60 * 2);
        long when = calendar.getTimeInMillis();
       /* if (!force) {
            when += delay;
        }*/

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

        ComponentName receiver = new ComponentName(ctx, CheckoutAlarmReceiver.class);
        PackageManager pm = ctx.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    private static PendingIntent getPendingIntent(Context ctx) {
        // Context ctx;   /* get the application context */
        Intent alarmIntent = new Intent(ctx, CheckoutAlarmReceiver.class);
        alarmIntent.setAction(CUSTOM_INTENT);

        return PendingIntent.getBroadcast(ctx, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private static PendingIntent getPendingIntentOpr(Context ctx) {
        // Context ctx;   /* get the application context */
        Intent alarmIntent = new Intent(ctx, CheckoutAlarmReceiver.class);
        alarmIntent.setAction(CUSTOM_INTENT);

        return PendingIntent.getBroadcast(ctx, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void turnOnScreen(Context context) {
        PowerManager.WakeLock screenLock = null;
        if ((context.getSystemService(POWER_SERVICE)) != null) {
            screenLock = ((PowerManager) context.getSystemService(POWER_SERVICE)).newWakeLock(
                    PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
            screenLock.acquire(4 * 1000 /*10 minutes*/);


            //  screenLock.release();
        }
    }

    /*private class Async_Checkout extends AsyncTask<String, Void, String> {
        String polyLine;
        String distanceFinal;
        Context context;
        private String jsonData;
        private String strDate;

        private Async_Checkout(Context context) {
            this.context = context;
        }



        protected String doInBackground(String... params) {
            db = new DatabaseHandler(context);

            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm:ss");
            strDate = sdftime.format(c.getTime());
            Log.e("Check_out_time1", strDate);

            try {
               *//* polyLine = params[0];
                distanceFinal = params[1];*//*
                OkHttpClient client = new OkHttpClient();
                 *//*For passing parameters*//*
                RequestBody formBody = new FormEncodingBuilder()
                        .add("type", "check_out_lat_lon")
                        .add("latlon", "0.0,0.0")
                        .add("check_out_time", strDate)
                        .add("tracking_id", ffmIDTrackingID)
                        .add("user_id", Common.getUserIdFromSP(context))
                        .add("distance", "0")
                        .add("route_snap", String.valueOf(0))
                        .add("route_snap_all", String.valueOf(0))
                        .add("route_snap_failure", String.valueOf(0))
                        .add("google_direction", String.valueOf(0))
                        .add("google_direction_all", String.valueOf(0))
                        .add("google_direction_failure", String.valueOf(0))
                        .add("pause", ""+LocationUpdatesService.getPauseFromSP(context))
                        .add("resume", ""+LocationUpdatesService.getResumeFromSP(context))
                        .add("route_path_lat_lon", "null")
                        .add("polyline", "")
                        .add("check_out_by", "2")
                        .build();

                Response responses = null;

                Log.d("body", "body:  " + Common.bodyToString(formBody));

                *//*MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(mediaType,
                        "type=check_in_lat_lon&visit_type=1&user_id=7&latlon=17.4411%2C78.3911&visit_date=2016-12-05%2C18%3A27%3A30&check_in_time=18%3A27%3A30&tracking_id=0");*//*
                Request request = new Request.Builder()
                        .url(Constants.URL_NSL_MAIN + Constants.URL_CHECKINOUT)
                        .post(formBody)
                        .addHeader("authorization", "Basic cmVzdDpzZWVkc0BhZG1pbg==")
                        .addHeader("content-type", "application/x-www-form-urlencoded")
                        .addHeader("cache-control", "no-cache")
                        .build();


                try {
                    responses = client.newCall(request).execute();
                    jsonData = responses.body().string();
                    System.out.println("!!!!!!!1 checkout" + jsonData);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonData;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (jsonData != null) {
                try {
                    // {"status":"success","msg":"successfully! done","tracking_id":"11723"}
                    JSONObject jsonObject = new JSONObject(jsonData);
                    if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("tracking_id", "");
                        editor.putString("checkinlatlong", "");
                        editor.putString("checkin", "false");
                        editor.commit();

                        SQLiteDatabase sdbw = db.getWritableDatabase();
                        String updatequerys = "UPDATE " + TABLE_EMPLOYEE_VISIT_MANAGEMENT + " SET " + KEY_EMP_STATUS + " = '" + "4" + "' WHERE " + KEY_EMP_VISIT_USER_ID + " = " + Common.getUserIdFromSP(context) + " and " + KEY_EMP_PLAN_DATE_TIME + " like '" + Common.getCurrentDateYYYYMMDD() + "%'";
                        sdbw.execSQL(updatequerys);

                        String updatequery1 = "UPDATE " + TABLE_GEO_TRACKING + " SET " + KEY_TABLE_GEO_TRACKING_UPDATED_STATUS + " = 1 ," + KEY_TABLE_GEO_TRACKING_CHECK_OUT_LAT_LONG + " = '" + "0.0,0.0" + "'," + KEY_TABLE_GEO_TRACKING_CHECK_OUT_TIME + " = '" + strDate + "'," + KEY_TABLE_GEO_TRACKING_POLYLINE + " = '" + polyLine + "'," + KEY_TABLE_GEO_TRACKING_DISTANCE + " = '" + distanceFinal + "' WHERE " + KEY_TABLE_GEO_TRACKING_ID + " = " + tid;

                        sdbw.execSQL(updatequery1);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
    }*/


}
