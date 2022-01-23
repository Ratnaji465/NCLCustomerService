/**
 * Copyright 2017 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ncl.nclcustomerservice.checkinout;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.ncl.nclcustomerservice.BuildConfig;
import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.activity.SplashActivity;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.database.DatabaseHandler;
import com.ncl.nclcustomerservice.network.APIInterface;
import com.ncl.nclcustomerservice.network.RetrofitResponseListener;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;
import com.ncl.nclcustomerservice.object.Geo_Tracking_POJO;
import com.ncl.nclcustomerservice.object.UpdateRoutePathReqVo;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A bound and started service that is promoted to a foreground service when location updates have
 * been requested and all clients unbind.
 * <p>
 * For apps running in the background on "O" devices, location is computed only once every 10
 * minutes and delivered batched every 30 minutes. This restriction applies even to apps
 * targeting "N" or lower which are run on "O" devices.
 * <p>
 * This sample show how to use a long-running service for location updates. When an activity is
 * bound to this service, frequent location updates are permitted. When the activity is removed
 * from the foreground, the service promotes itself to a foreground service, and location updates
 * continue. When the activity comes back to the foreground, the foreground service stops, and the
 * notification assocaited with that service is removed.
 */
public class LocationUpdatesService extends Service implements RetrofitResponseListener {

    private static final String PACKAGE_NAME =
            "com.suprasoft.ncl";

    private static final String TAG = LocationUpdatesService.class.getSimpleName();

    /**
     * The name of the channel for notifications.
     */
    private static final String CHANNEL_ID = "channel_01";

    public static final String ACTION_BROADCAST = PACKAGE_NAME + ".broadcast";
    public static final String ACTION_BROADCAST_LOCATION_MAP = PACKAGE_NAME + ".broadcastmap";

    public static final String EXTRA_LOCATION = PACKAGE_NAME + ".location";
    public static final String EXTRA_STARTED_FROM_NOTIFICATION = PACKAGE_NAME +
            ".started_from_notification";
    public static final String SCREEN_WAKE_TIME = "screen_wake_time";
    private static final String EXTRA_STARTED_FROM_PAUSED = "EXTRA_STARTED_FROM_PAUSED";
    private static final String EXTRA_STARTED_FROM_RESUME = "EXTRA_STARTED_FROM_RESUME";
    public static final String EXTRA_LOCATION_SAVED = "extra_location_saved";
    public static final String ACTION_BROADCAST_PAUSED_LOCATION_SAVED = "action_broadcast_paused_location_saved";
    public static final String EXTRA_LOCATION_PAUSED = "extra_location_paused";
    private static String pauseFormDB = null;
    private static String resumeFormDB = null;


    private final IBinder mBinder = new LocalBinder();

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 2000;

    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    /**
     * The identifier for the notification displayed for the foreground service.
     */
    private static final int NOTIFICATION_ID = 12345678;

    /**
     * Used to check whether the bound activity has really gone away and not unbound as part of an
     * orientation change. We create a foreground service notification only if the former takes
     * place.
     */
    private boolean mChangingConfiguration = false;

    public static NotificationManager mNotificationManager;

    /**
     * Contains parameters used by {@link com.google.android.gms.location.FusedLocationProviderApi}.
     */
    public static LocationRequest mLocationRequest;

    /**
     * Provides access to the Fused Location Provider API.
     */
    public static FusedLocationProviderClient mFusedLocationClient;

    /**
     * Callback for changes in location.
     */
    public static LocationCallback mLocationCallback;

    private Handler mServiceHandler;

    /**
     * The current location.
     */
    public static Location mLocation;
    private Location oldLocation1;
    private static float DISTANCE = 150;
    private static float accuracyLocation;
    private static float MAX_SPEED = 10;
    private static String checkinlatlong;
    static String trackingId;
    static String tid;
    String existedroutepath = "";
    static DatabaseHandler db;
    static SQLiteDatabase sdbw;
    SQLiteDatabase sdbr;
    //    private static String ffmID;
//    public static SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static String geoTrackingId;
    private Handler handler;
    private Runnable runable;
    private AlarmManager alarmManager;
    public static String checkInTime;
    public static Timer timer;
    private ActionClick actionClick;
    private static NotificationCompat.Builder notificationBuilder;
    private AppKilledNotificationReciver appKilledNotificationReciver;
    private static int userId;
    private static APIInterface apiInterface;
    private String finalExistedroutepath;

    public LocationUpdatesService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate..........");
        //savePauseStatusSP(LocationUpdatesService.this, false);

        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        mServiceHandler = new Handler(handlerThread.getLooper());
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder().addInterceptor(interceptor)
                .connectTimeout(Constants.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(Constants.READ_TIME_OUT, TimeUnit.SECONDS)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiInterface = retrofit.create(APIInterface.class);

//        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        db = DatabaseHandler.getDatabase(this);
        userId = Common.getUserIdFromSP(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        actionClick = new ActionClick();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.nsl.beejtantra.pause.location");
        intentFilter.addAction("com.nsl.beejtantra.resume.location");
        this.registerReceiver(actionClick, intentFilter);

        appKilledNotificationReciver = new AppKilledNotificationReciver();
        this.registerReceiver(appKilledNotificationReciver, new IntentFilter("app.notification.killed"));

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult == null) {
                    return;
                }


                onNewLocation(locationResult.getLastLocation());
                // savePointsFromSP(LocationUpdatesService.this, getPointsFromSP(LocationUpdatesService.this) + "\n" + getLocationResultText(locationResult.getLocations()));
                for (Location location : locationResult.getLocations()) {
                   /* Common.saveLastFiveLocatins(LocationUpdatesService.this,location);
                    if(Common.shouldAddInDb(LocationUpdatesService.this)==null){
                        Common.Log.i("Size : returned" + Common.getLastFiveLocatins(LocationUpdatesService.this).toString());
                        return;
                    }*/
                    // Update UI with location data
                    Common.Log.i("Size : " + locationResult.getLocations().size() + "\n" + location.toString());
                    if (location != null && location.hasAccuracy()) {

                        if (oldLocation1 != null && !Common.isSpeedBelow120KPH(oldLocation1.distanceTo(location), location.getTime() - oldLocation1.getTime(), 27)) {
                            oldLocation1 = location;
                            continue;
                        } else if (oldLocation1 == null) {
                            oldLocation1 = location;
                        }

                        trackingId = Common.getDefaultSP(LocationUpdatesService.this).getString("tracking_id", null);
                        if (trackingId != null && trackingId != "") {
                            // afficher(location);
                            new AysncProcessRequest(getApplicationContext()).execute(location);
                        }
                        //turnOnScreen();
                    }

                }

            }
        };

        createLocationRequest(0);
        getLastLocation();
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                removeLocationUpdates();
                createLocationRequest(10);
                requestLocationUpdates(11);
            }
        }, 20000);
*/
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            // Create the channel for the notification
            NotificationChannel mChannel =
                    new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            mChannel.setDescription("no sound");
            mChannel.setSound(null, null);
            mChannel.enableLights(false);
            mChannel.setLightColor(Color.BLUE);
            mChannel.enableVibration(false);

            // Set the Notification Channel for the Notification Manager.
            mNotificationManager.createNotificationChannel(mChannel);
        }

       /* Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Common.Log.i(TAG + "abc" + e.toString());
                stopSelf();
                startService(new Intent(LocationUpdatesService.this, LocationUpdatesService.class));
            }
        });*/
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand Service started");
        boolean startedFromNotification = intent.getBooleanExtra(EXTRA_STARTED_FROM_NOTIFICATION,
                false);

        boolean startedFromJob = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            startedFromJob = intent.getBooleanExtra(JobScheduleService.STARTED_FROM,
                    false);
        }

        if (startedFromJob) {
            if (isCheckedIn(LocationUpdatesService.this))
                startForeground(NOTIFICATION_ID, getNotification());
            requestLocationUpdates(2);
        }


        // Tells the system to not try to recreate the service after it has been killed.
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mChangingConfiguration = true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Called when a client (OreoActivity in case of this sample) comes to the foreground
        // and binds with this service. The service should cease to be a foreground service
        // when that happens.
        Log.i(TAG, "in onBind()");
        stopForeground(true);
        mChangingConfiguration = false;
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        // Called when a client (OreoActivity in case of this sample) returns to the foreground
        // and binds once again with this service. The service should cease to be a foreground
        // service when that happens.
        Log.i(TAG, "in onRebind()");
        stopForeground(true);
        mChangingConfiguration = false;
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "Last client unbound from service");
       /* MyWakefulReceiver myWakefulReceiver=new MyWakefulReceiver();
        myWakefulReceiver.setAlarm(this);*/

        // Called when the last client (OreoActivity in case of this sample) unbinds from this
        // service. If this method is called due to a configuration change in OreoActivity, we
        // do nothing. Otherwise, we make this service a foreground service.
        if (!mChangingConfiguration && Utils.requestingLocationUpdates(this)) {
            Log.i(TAG, "Starting foreground service");

            // TODO(developer). If targeting O, use the following code.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
               /* ContextCompat.startForegroundService(this,new Intent(this,
                        LocationUpdatesService.class));*/
                //.startServiceInForeground(new Intent(this,
                // LocationUpdatesService.class), NOTIFICATION_ID, getNotification());
            } /*else {
                startForeground(NOTIFICATION_ID, getNotification());
            }*/
            if (isCheckedIn(LocationUpdatesService.this))
                startForeground(NOTIFICATION_ID, getNotification());
        }
        return true; // Ensures onRebind() is called when a client re-binds.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy... Service");
        mServiceHandler.removeCallbacksAndMessages(null);
        removeLocationUpdates();
        if (timer != null)
            timer.cancel();
        if (actionClick != null)
            unregisterReceiver(actionClick);
        if (appKilledNotificationReciver != null)
            unregisterReceiver(appKilledNotificationReciver);
        // startForeground(NOTIFICATION_ID, getNotification());
        savePauseStatusSP(LocationUpdatesService.this, false);
        stopForeground(true);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // Common.writeFile(TAG+"  onTaskRemoved");
        Log.d(TAG, "onTaskRemoved..1");

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isCheckedIn(LocationUpdatesService.this) && !isPaused(LocationUpdatesService.this)) {
                    requestLocationUpdates(3);
                    startForeground(NOTIFICATION_ID, getNotification());
                }

            }
        }, 10000);

        super.onTaskRemoved(rootIntent);
        Log.d(TAG, "onTaskRemoved..2");

    }


    @Override
    public void onLowMemory() {
        //    Common.writeFile(TAG + "  onLowMemory");

        super.onLowMemory();
        Log.d(TAG, "onLowMemory..");

    }

    /**
     * Makes a request for location updates. Note that in this sample we merely log the
     * {@link SecurityException}.
     *
     * @param i
     */
    public void requestLocationUpdates(int i) {
        Log.i(TAG, "Requesting location updates" + i);
        Utils.setRequestingLocationUpdates(this, true);
        startService(new Intent(getApplicationContext(), LocationUpdatesService.class));
        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback, Looper.myLooper());
        } catch (SecurityException unlikely) {
            Utils.setRequestingLocationUpdates(this, false);
            Log.e(TAG, "Lost location permission. Could not request updates. " + unlikely);
        }
    }

    public static void requestLocationUpdates(FusedLocationProviderClient mFusedLocationClient, LocationRequest mLocationRequest, LocationCallback mLocationCallback) {
        Log.i(TAG, "Requesting location updates");
        //  Utils.setRequestingLocationUpdates(, true);
//        startService(new Intent(getApplicationContext(), LocationUpdatesService.class));
        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback, Looper.myLooper());
        } catch (SecurityException unlikely) {
            // Utils.setRequestingLocationUpdates(this, false);
            Log.e(TAG, "Lost location permission. Could not request updates. " + unlikely);
        }
    }

    public void setAlarm() {
        Log.i(TAG, "setAlarm....");
        try {
            // stopSelf();
            Intent intent = new Intent(this, LocationUpdatesService.class);
            PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
            alarmManager.cancel(pintent);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 20000, 2 * 60 * 1000, pintent);

            ComponentName receiver = new ComponentName(this, LocationUpdatesService.class);
            PackageManager pm = this.getPackageManager();

            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
        } catch (Exception e) {

            Log.e(TAG, "Lost location permission. Could not request updates. " + e);
        }
    }


    public void cancelAlarm() {
        Log.i(TAG, "cancelAlarm....");
        try {
            Intent intent = new Intent(this, LocationUpdatesService.class);
            PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
            alarmManager.cancel(pintent);
            //  alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+20000, 4*60*1000, pintent);

        } catch (Exception e) {

            Log.e(TAG, "Lost location permission. Could not request updates. " + e);
        }
    }

    /**
     * Removes location updates. Note that in this sample we merely log the
     * {@link SecurityException}.
     */
    public void removeLocationUpdates() {
        Log.i(TAG, "Removing location updates");
        try {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
            Utils.setRequestingLocationUpdates(this, false);
            stopForeground(true);
            stopSelf();
        } catch (SecurityException unlikely) {
            Utils.setRequestingLocationUpdates(this, true);
            Log.e(TAG, "Lost location permission. Could not remove updates. " + unlikely);
        }
    }

    public static void removeLocationUpdatesFromNotification() {
        Log.i(TAG, "Removing location updates static");
        try {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
          /*  Utils.setRequestingLocationUpdates(this, false);
            stopSelf();*/
        } catch (SecurityException unlikely) {
            // Utils.setRequestingLocationUpdates(this, true);
            Log.e(TAG, "Lost location permission. Could not remove updates. " + unlikely);
        }
    }

    /**
     * Returns the {@link NotificationCompat} used as part of the foreground service.
     */
    private Notification getNotification() {
        cancelTimer();
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.custom_push);
        Intent intent = new Intent();

        CharSequence text = Utils.getLocationText(mLocation);
        isCheckedIn(this);
        // Extra to help us figure out if we arrived in onStartCommand via the notification or not.
        intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION, true);
        intent.putExtra(EXTRA_STARTED_FROM_PAUSED, true);
        intent.putExtra(EXTRA_STARTED_FROM_RESUME, false);
        intent.setAction("com.nsl.beejtantra.pause.location");
        // The PendingIntent that leads to a call to onStartCommand() in this service.
        PendingIntent servicePendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        Intent intentResume = new Intent();

        // Extra to help us figure out if we arrived in onStartCommand via the notification or not.
        intentResume.putExtra(EXTRA_STARTED_FROM_NOTIFICATION, true);
        intentResume.putExtra(EXTRA_STARTED_FROM_RESUME, true);
        intentResume.putExtra(EXTRA_STARTED_FROM_PAUSED, false);
        intentResume.setAction("com.nsl.beejtantra.resume.location");

        // The PendingIntent that leads to a call to onStartCommand() in this service.
        PendingIntent servicePendingIntentResume = PendingIntent.getBroadcast(this, 0, intentResume,
                PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setOnClickPendingIntent(R.id.rl_pause, servicePendingIntent);
//        remoteViews.setOnClickPendingIntent(R.id.rl_resume, servicePendingIntentResume);


        Intent activityIntent = new Intent(this, SplashActivity.class);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        // The PendingIntent to launch activity.
        PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0,
                activityIntent, 0);

        Notification notification = new Notification();
        notification.defaults = 0;
        notification.contentView = remoteViews;
        String checkInTimee = "Check-in Time: ";
        if (checkInTime != null) {
            long time = System.currentTimeMillis() - Common.timeToMillliSecond(checkInTime);
            if (time > 0) {
                checkInTimee = "Check-in Time: " + DurationFormatUtils.formatDuration(time, "HH:mm:ss");
                remoteViews.setTextViewText(R.id.text, checkInTimee);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setDefaults(notification.defaults)
                // .addAction(R.drawable.ic_launch, getString(R.string.launch_activity),
                //         activityPendingIntent)
//                .addAction(R.drawable.cast_ic_expanded_controller_play, "Pause",servicePendingIntent)
//                .addAction(R.drawable.cast_ic_expanded_controller_play, "Resume",servicePendingIntentResume)
                .setContentIntent(activityPendingIntent)
                .setCustomBigContentView(remoteViews)
               /*.addAction(R.drawable.ic_cancel, getString(R.string.remove_location_updates),
                        servicePendingIntent)*/
                .setContentTitle(checkInTimee)
                // .setContentText("Time:")
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(null)
                .setShowWhen(false)
                .setSmallIcon(R.mipmap.ic_launcher);
        //   .setTicker(text);
        //   .setWhen(System.currentTimeMillis());
        // Set the Channel ID for Android O.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID); // Channel ID
        }
        setNotifyNotificationPause(builder);
        if (isPaused(LocationUpdatesService.this)) {
//            remoteViews.setViewVisibility(R.id.rl_pause, View.GONE);
//            remoteViews.setViewVisibility(R.id.rl_resume, View.VISIBLE);
        } else {
            getCheckInTime(LocationUpdatesService.this, builder);
        }

        return builder.build();
    }

    public static void getLastLocation() {
        try {
            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                mLocation = task.getResult();
                                //    onNewLocation(mLocation);
                            } else {
                                Log.w(TAG, "Failed to get location.");
                            }
                        }
                    });
        } catch (SecurityException unlikely) {
            Log.e(TAG, "Lost location permission." + unlikely);
        }
    }

    private void onNewLocation(Location location) {
        Log.i(TAG, "New location: " + location);

        mLocation = location;

        // Notify anyone listening for broadcasts about the new location.
        Intent intent = new Intent(ACTION_BROADCAST);
        intent.putExtra(EXTRA_LOCATION, location);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

        // Update notification content if running as a foreground service.
/*
        if (serviceIsRunningInForeground(this)) {
             mNotificationManager.notify(NOTIFICATION_ID, getNotification());


            PowerManager pm = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
            boolean isScreenOn = pm.isScreenOn();
            Log.e("screen on....", ""+isScreenOn);
            if(isScreenOn==false && (System.currentTimeMillis()-Common.getLongDataFromSP(this,SCREEN_WAKE_TIME))>2*60*1000)
            {
                Common.saveLongDataInSP(this,SCREEN_WAKE_TIME,System.currentTimeMillis());
                PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE,"MyLock");
               // wl.acquire(5000);
                PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyCpuLock");

                wl_cpu.acquire(1000);
            }
        }
*/
    }

    /**
     * Sets the location request parameters.
     */
    private void createLocationRequest(float displacement) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(displacement);
    }

    @Override
    public void onResponseSuccess(ApiResponseController objectResponse, ApiRequestController objectRequest, ProgressDialog progressDialog) {
        try {
            if (objectResponse.code == 200) {
                db.commonDao().updateRoutePath(finalExistedroutepath, 1, trackingId);
                Common.Log.i("Route Path " + finalExistedroutepath);
            }
        } catch (Exception e) {

        }
    }


    /**
     * Class used for the client Binder.  Since this service runs in the same process as its
     * clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public LocationUpdatesService getService() {
            return LocationUpdatesService.this;
        }
    }

    /**
     * Returns true if this is a foreground service.
     *
     * @param context The {@link Context}.
     */
    public static boolean serviceIsRunningInForeground(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
                Integer.MAX_VALUE)) {
            if (LocationUpdatesService.class.getName().equals(service.service.getClassName())) {
                if (service.foreground) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getLocationResultText(List<Location> mLocations) {
        if (mLocations.isEmpty()) {
            return "Unknown Location";
        }
        StringBuilder sb = new StringBuilder();
        for (Location location : mLocations) {
            sb.append("(");
            sb.append(location.getLatitude());
            sb.append(", ");
            sb.append(location.getLongitude());
            sb.append(", ");
            sb.append(location.getAccuracy());
            sb.append(", ");
            sb.append(getCurrentDateAndTime());
            sb.append(", ");
            sb.append(location.getProvider());
            sb.append(")");
            sb.append("\n");
        }
        return sb.toString();
    }

    public static SharedPreferences getDefaultSP(Context context) {

        return PreferenceManager.getDefaultSharedPreferences(context);

    }


    public static void savePointsFromSP(Context context, String sb) {

        getDefaultSP(context).edit().putString("points", sb).commit();

    }

    public static String getPointsFromSP(Context context) {

        return getDefaultSP(context).getString("points", "");

    }

    public static void savePauseSP(Context context, String sb) {

        getDefaultSP(context).edit().putString("pause", sb).commit();

    }

    public static String getPauseFromSP(Context context) {

        return getDefaultSP(context).getString("pause", null);

    }

    public static void savePauseStatusSP(Context context, boolean sb) {

        getDefaultSP(context).edit().putBoolean("pause_resume_status", sb).commit();

    }

    public static boolean isPaused(Context context) {

        return getDefaultSP(context).getBoolean("pause_resume_status", false);

    }

    public static void saveResumeSP(Context context, String sb) {

        getDefaultSP(context).edit().putString("resume", sb).commit();

    }

    public static String getResumeFromSP(Context context) {

        return getDefaultSP(context).getString("resume", "");

    }

    public static String getCurrentDateAndTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    private static void afficher(Context context, Location mLocation) {

        DISTANCE = Common.getDistanceByAccuracy(mLocation.getAccuracy());
        double latitude = mLocation.getLatitude();
        double longitude = mLocation.getLongitude();
        accuracyLocation = mLocation.getAccuracy();

        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        Log.d("save_lat_long.1", "latitude " + latitude);
        Location locationFromSp = Common.getCurrentLocationFromSP(context);
        if (locationFromSp.getLatitude() == 0 && locationFromSp.getLongitude() == 0) {
            Log.d("save_lat_long.2", "latitude " + latitude);
            Common.saveCurrentLocationInSP(context, latitude, longitude, mLocation.getAccuracy(), System.currentTimeMillis(), 15);
        } else {

            float distance = location.distanceTo(locationFromSp);
            float bearingTo = location.bearingTo(locationFromSp);
            float speedFromLastPoint = Common.calculateMaxSpeed(distance, System.currentTimeMillis() - locationFromSp.getTime());
            MAX_SPEED = speedFromLastPoint > locationFromSp.getSpeed() ? speedFromLastPoint : locationFromSp.getSpeed();
            MAX_SPEED = MAX_SPEED > 27 ? 27 : MAX_SPEED;
            Log.d("save_lat_long.3", "latitude " + latitude + "\n distance: " + distance + " bearingTo: " + bearingTo);
            if (distance < DISTANCE || !Common.isSpeedBelow120KPH(distance, System.currentTimeMillis() - locationFromSp.getTime(), MAX_SPEED)) {
                Log.d("save_lat_long.5", "latitude " + latitude);
                return;
            } else {
                Log.d("save_lat_long.6", "latitude " + latitude);
                broadcastLocation(context, location);
                Common.saveCurrentLocationInSP(context, latitude, longitude, mLocation.getAccuracy(), System.currentTimeMillis(), MAX_SPEED);
                int a = Common.haveInternet(context) ? 1 : 0;

                checkinlatlong = String.valueOf(latitude) + "," + String.valueOf(longitude) + "," + accuracyLocation + "," + a + "," + Common.currentTimeFromMS(System.currentTimeMillis()) + ",0";
                //Toast.makeText(getApplicationContext(),checkinlatlong,Toast.LENGTH_LONG).show();
                updateLocationInDB(context, checkinlatlong);
            }
        }
    }

    private static void updateLocationInDB(Context context, String checkinlatlong) {
        String existedroutepath = null;
        Geo_Tracking_POJO geo_tracking_pojo = db.commonDao().getGeoTrackingData(Common.getCurrentDate(), userId);
        if (geo_tracking_pojo != null) {
            existedroutepath = geo_tracking_pojo.routePathLatLon;
            if (existedroutepath == null) {
                existedroutepath = checkinlatlong;
            } else {
                existedroutepath = existedroutepath + ":" + checkinlatlong;
            }
            db.commonDao().updateRoutePath(existedroutepath, 0, geo_tracking_pojo.trackingId);
        }

        if (Common.haveInternet(context)) {
            trackingId = Common.getDefaultSP(context).getString("tracking_id", null);
            if (trackingId == null) {
                return;
            } else {

                UpdateRoutePathReqVo updateRoutePathReqVo = new UpdateRoutePathReqVo();
                updateRoutePathReqVo.trackingId = trackingId;
                updateRoutePathReqVo.latlon = existedroutepath;
                String finalExistedroutepath = existedroutepath;
                //    new RetrofitRequestController(LocationUpdatesService.this).sendRequest(Constants.RequestNames.GEO_UPDATE_PATH, updateRoutePathReqVo, false, apiInterface);
//                new Async_Routepath(context, existedroutepath,pauseP,resumeP).execute();

                final ApiRequestController apiRequestController = new ApiRequestController();
                apiRequestController.requesterid = Common.getUserIdFromSP(context);
                //  apiRequestController.requesterid =36;
                apiRequestController.requestname = Constants.RequestNames.GEO_UPDATE_PATH;
                apiRequestController.requestparameters = updateRoutePathReqVo;

                //   Common.Log.i("request :"+ new Gson().toJson(apiRequestController));

                if (apiInterface != null) {
                    apiInterface.callPost(Constants.API, apiRequestController).enqueue(new Callback<ApiResponseController>() {
                        @Override
                        public void onResponse(Call<ApiResponseController> call, retrofit2.Response<ApiResponseController> response) {

                            try {
                                Common.Log.i("Response: " + new Gson().toJson(response.body()));
                                if (response.body() == null) {
                                    // Common.dismissProgressDialog(progressDialog);
                                    Toast.makeText(context, "Intenal Server Error", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (response.body().code != Constants.SUCCESS) {
                                    // Common.dismissProgressDialog(progressDialog);
                                    Toast.makeText(context, response.body().message, Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (response.body().code == 200) {
                                    db.commonDao().updateRoutePath(finalExistedroutepath, 1, trackingId);
                                    Common.Log.i("Route Path " + finalExistedroutepath);
                                }
                                //  retrofitResponseListener.onResponseSuccess(response.body(), apiRequestController, progressDialog);
                                // Common.Log.i("Response: absajk");
                            } catch (Exception e) {
                                //// Common.disPlayExpection(e, progressDialog);
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponseController> call, Throwable t) {
                            Common.Log.i("retrofit " + t.toString());
                            //  Common.dismissProgressDialog(progressDialog);
                        }
                    });
                }
            }
        }

    }
   /* public static class Async_Routepath extends AsyncTask<Void, Void, String> {

        private final Context context;
        private String path="";
        private String jsonData="";
        String pauseP="";
        String resumeP="";
        public Async_Routepath(Context context, String path, String pauseP, String resumeP) {
            this.context = context;
            this.path = path;
            this.pauseP=pauseP;
            this.resumeP=resumeP;

        }

        protected String doInBackground(Void... params) {

            try {

                OkHttpClient client = new OkHttpClient();
                 *//*For passing parameters*//*
                RequestBody formBody = new FormEncodingBuilder()
                        .add("latlon", isStringNull(path))
                        .add("tracking_id", trackingId)
                        .add("pause", isStringNull(pauseP))
                        .add("resume", isStringNull(resumeP))
                        .build();

                Response responses = null;

                *//*MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(mediaType,
                        "type=check_in_lat_lon&visit_type=1&user_id=7&latlon=17.4411%2C78.3911&visit_date=2016-12-05%2C18%3A27%3A30&check_in_time=18%3A27%3A30&tracking_id=0");*//*
                Request request = new Request.Builder()
                        .url(Constants.URL_NSL_MAIN + Constants.URL_ROUTEPATH_UPDATE_INTERVAL)
                        .post(formBody)
                        .addHeader("authorization", "Basic cmVzdDpzZWVkc0BhZG1pbg==")
                        .addHeader("content-type", "application/x-www-form-urlencoded")
                        .addHeader("cache-control", "no-cache")
                        .build();


                try {
                    responses = client.newCall(request).execute();
                    jsonData = responses.body().string();
                    System.out.println("!!!!!!!1" + jsonData + " a  " + path.length());
                    if (jsonData != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(jsonData);
                            if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(SYNC_STATUS, 1);
                                db.updateTable(TABLE_GEO_TRACKING, contentValues, KEY_TABLE_GEO_TRACKING_FFMID, jsonObject.getString("tracking_id"));

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonData;
        }


    }*/

    public static boolean isCheckedIn(Context context) {
        boolean isCheckedIn = false;
        if (Common.getDefaultSP(context).getString("checkin", "").equalsIgnoreCase("true")) {
            isCheckedIn = true;
            checkInTime = Common.getDefaultSP(context).getString("check_in_time", "");
        }
        return isCheckedIn;
    }

    public static void getCheckInTime(final Context context, final NotificationCompat.Builder notification) {
        if (isCheckedIn(context) && checkInTime != null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (isPaused(context)) {
                        cancelTimer();
                        return;
                    }
                    if (serviceIsRunningInForeground(context)) {
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                                R.layout.custom_push);

                        long time = System.currentTimeMillis() - Common.timeToMillliSecond(checkInTime);
                        if (time > 0) {
                            String checkInTimee = "Check-in Time: " + DurationFormatUtils.formatDuration(time, "HH:mm:ss");
                            remoteViews.setTextViewText(R.id.text, checkInTimee);

                            if (notification != null) {
                                notification.setCustomBigContentView(remoteViews);
                                notification.setContentTitle(checkInTimee);
                                mNotificationManager.notify(NOTIFICATION_ID, notification.build());
                            }
                        }

                    }

                }
            }, 1000, 1000);

        }
    }

    public static void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }



    public static class AysncProcessRequest extends AsyncTask<Location, Void, Void> {
        private final Context context;

        AysncProcessRequest(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Location... locations) {
            afficher(context, locations[0]);
            return null;
        }
    }

    public static class ActionClick extends BroadcastReceiver {


        private Timer timerPause;

        @Override
        public void onReceive(final Context context, Intent intent) {
            Log.d("onReceive ", intent.getAction());
            if (intent.getAction().equalsIgnoreCase("com.nsl.beejtantra.pause.location")) {
                pauseBroadcast(context, intent);

            } else if (intent.getAction().equalsIgnoreCase("com.nsl.beejtantra.resume.location")) {
                resumeBroadcast(context, intent);
            } else if (intent.getAction().equalsIgnoreCase("com.nsl.beejtantra.pause.location1")) {
                pauseBroadcast(context, intent);

            } else if (intent.getAction().equalsIgnoreCase("com.nsl.beejtantra.resume.location1")) {
                resumeBroadcast(context, intent);
            }


        }

        private void resumeBroadcast(final Context context, Intent intent) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.custom_push);
//            remoteViews.setViewVisibility(R.id.rl_pause, View.VISIBLE);
//            remoteViews.setViewVisibility(R.id.rl_resume, View.GONE);

            final NotificationCompat.Builder builder = getNotifyNotificationPause();
            if (builder != null) {
                //getText(builder.build());
                builder.setCustomBigContentView(remoteViews);
                mNotificationManager.notify(NOTIFICATION_ID, builder.build());
            } else {
                Intent intent1 = new Intent();
                intent1.setAction("app.notification.killed");
                context.sendBroadcast(intent1);
            }

            requestLocationUpdates(mFusedLocationClient, mLocationRequest, mLocationCallback);

            timerPause = new Timer();
            final List<Location> list = new ArrayList<>();
            final Location[] location = {null};
            TimerTask timerTask = new TimerTask() {
                int c = 0;

                @Override
                public void run() {
                    getLastLocation();
                    if (mLocation != null)
                        list.add(mLocation);

                    if (c == 7) {
                        if (timerPause != null) {
                            timerPause.cancel();
                            timerPause = null;
                        }

                        for (int i = 0; i < list.size() - 1; i++) {
                            if (location[0] == null) {
                                location[0] = mLocation;
                            }
                            if (location[0].getAccuracy() > list.get(i + 1).getAccuracy()) {
                                location[0] = list.get(i + 1);
                            }
                        }

                        int a = Common.haveInternet(context) ? 1 : 0;
                        String timeLocation = location[0] == null ? null : location[0].getLatitude() + "," + location[0].getLongitude() + "," + location[0].getAccuracy() + "," + a + "," + Common.currentTimeFromMS(System.currentTimeMillis()) + ",2";
                        String locationPoints = timeLocation;
                        if (timeLocation != null && isCheckedIn(context)) {
                           /* savePauseSP(context, getPauseFromSP(context) + ":" + timeLocation);
                            String previousPoints = getPauseFromSP(context);*/
                            if (resumeFormDB != null) {
                                timeLocation = resumeFormDB + ":" + timeLocation;
                            }
                            ContentValues contentValues = new ContentValues();
//                            contentValues.put(RESUME, timeLocation);
//                            db.updateTable(TABLE_GEO_TRACKING, contentValues, KEY_TABLE_GEO_TRACKING_ID, geoTrackingId);
                            savePauseStatusSP(context, false);
                            updateLocationInDB(context, locationPoints);
                            broadcastCloseProgress(context, true, false);
                            getCheckInTime(context, builder);

                        } else {
                            broadcastCloseProgress(context, false, false);
                        }

                    }
                    c++;
                }
            };
            timerPause.scheduleAtFixedRate(timerTask, 100, 1000);


        }

        private void pauseBroadcast(final Context context, Intent intent) {
            cancelTimer();
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.custom_push);
//            remoteViews.setViewVisibility(R.id.rl_pause, View.GONE);
//            remoteViews.setViewVisibility(R.id.rl_resume, View.VISIBLE);

            NotificationCompat.Builder builder = getNotifyNotificationPause();
            if (builder != null) {
                //getText(builder.build());
                long time = System.currentTimeMillis() - Common.timeToMillliSecond(checkInTime);
                if (time > 0) {
                    String checkInTimee = "Check-in Time: " + DurationFormatUtils.formatDuration(time, "HH:mm:ss");
                    remoteViews.setTextViewText(R.id.text, checkInTimee);
                    builder.setCustomBigContentView(remoteViews);
                    builder.setContentTitle(checkInTimee);

                    // builder.setCustomBigContentView(remoteViews);
                    mNotificationManager.notify(NOTIFICATION_ID, builder.build());
                }
            }

            timerPause = new Timer();
            final List<Location> list = new ArrayList<>();
            final Location[] location = {null};
            TimerTask timerTask = new TimerTask() {
                int c = 0;

                @Override
                public void run() {
                    getLastLocation();
                    if (mLocation != null)
                        list.add(mLocation);

                    if (c == 5) {
                        if (timerPause != null) {
                            timerPause.cancel();
                            timerPause = null;
                        }
                        for (int i = 0; i < list.size() - 1; i++) {
                            if (location[0] == null) {
                                location[0] = mLocation;
                            }
                            if (location[0].getAccuracy() > list.get(i + 1).getAccuracy()) {
                                location[0] = list.get(i + 1);
                            }
                        }

                        int a = Common.haveInternet(context) ? 1 : 0;
                        String timeLocation = location[0] == null ? null : location[0].getLatitude() + "," + location[0].getLongitude() + "," + location[0].getAccuracy() + "," + a + "," + Common.currentTimeFromMS(System.currentTimeMillis()) + ",1";
                        String locationPoints = timeLocation;
                        if (timeLocation != null && isCheckedIn(context)) {
                           /* savePauseSP(context, getPauseFromSP(context) + ":" + timeLocation);
                            String previousPoints = getPauseFromSP(context);*/
                            if (pauseFormDB != null) {
                                timeLocation = pauseFormDB + ":" + timeLocation;
                            }
                            ContentValues contentValues = new ContentValues();
//                            contentValues.put(PAUSE, timeLocation);
//                            db.updateTable(TABLE_GEO_TRACKING, contentValues, KEY_TABLE_GEO_TRACKING_ID, geoTrackingId);
                            savePauseStatusSP(context, true);
                            updateLocationInDB(context, locationPoints);
                            broadcastCloseProgress(context, true, true);
                        } else {
                            broadcastCloseProgress(context, false, true);
                        }
                        removeLocationUpdatesFromNotification();
                    }
                    c++;
                }
            };
            timerPause.scheduleAtFixedRate(timerTask, 100, 1000);


        }
    }

    public void setNotifyNotificationPause(NotificationCompat.Builder builder) {
        this.notificationBuilder = builder;

    }

    public static NotificationCompat.Builder getNotifyNotificationPause() {
        return notificationBuilder;

    }

    public class AppKilledNotificationReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, intent.getAction());
            if (isCheckedIn(LocationUpdatesService.this))
                startForeground(NOTIFICATION_ID, getNotification());
        }
    }

    public static void broadcastLocation(Context context, Location location) {
        Log.i(TAG, "New location: " + location);

        // Notify anyone listening for broadcasts about the new location.
        Intent intent = new Intent(ACTION_BROADCAST_LOCATION_MAP);
        intent.putExtra(EXTRA_LOCATION, location);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }


    public static void broadcastCloseProgress(Context context, boolean status, boolean paused) {
        Log.i(TAG, "broadcastCloseProgress ");
        // Notify anyone listening for broadcasts about the new location.
        Intent intent = new Intent(ACTION_BROADCAST_PAUSED_LOCATION_SAVED);
        intent.putExtra(EXTRA_LOCATION_SAVED, status);
        intent.putExtra(EXTRA_LOCATION_PAUSED, paused);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }
}
