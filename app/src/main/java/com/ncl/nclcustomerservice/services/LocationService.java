package com.ncl.nclcustomerservice.services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import android.util.Log;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.ncl.nclcustomerservice.commonutils.Common;

import java.util.Iterator;


/**
 * Created by sys on 5/30/2017.
 */

public class LocationService extends Service implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GpsStatus.Listener {

    public static final String BROADCAST_LOCATIONS = "broadcast_locations";
    private static float MAX_SPEED = 0;
    ;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    public String checkinlatlong;
    public SharedPreferences sharedpreferences;
    private String jsonData;
    volatile boolean isPushToServer = false;
    public String trackingId, tid, existedroutepath;
    SQLiteDatabase sdbw, sdbr;
    public static final String mypreference = "mypref";
    private String ffmID;
    private String accuracy;
    private LocationService binder;
    private String geoTrackingId;
    private float accuracyLocation;
    int count = 0;
    private Handler handler;
    private SharedPreferences preferences;
    private PowerManager.WakeLock wakeLock;
    public OnLocationUpdateListener onLocationUpdateListener;
    private Location oldLocation;
    private LocationManager mService;
    private LocationCallback locationCallBcak;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location oldLocation1;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(3000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences("mypref", MODE_PRIVATE);
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);

        //wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DoNotSleep");
        // wakeLock.acquire();
        mService = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Common.Log.i("before location permission");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Common.Log.i("location permission not granted");
            return;
        }
        //   mService.addGpsStatusListener(this);
        Log.d("LocationService", "LocationService...");
        createLocationRequest();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        locationCallBcak = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult == null) {
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    Common.Log.i("Location Callback");
                    Common.Log.i("Size : " + locationResult.getLocations().size() + "\n" + location.toString());
                    if (location != null && location.hasAccuracy()) {

                        broadcastLocation(location);
                    }
                }
            }
        };
    }

    @Override
    public void onStart(Intent intent, int startId) {
        //  mGoogleApiClient.connect();
        super.onStart(intent, startId);
    }

    @Override
    public void onLocationChanged(Location location) {
Common.Log.i("Location changed");
        Log.d("Location Service", location.toString() + "location speed " + location.getSpeed());

        if (location != null && location.hasAccuracy()) {
            //    accuracy = accuracy + String.valueOf(location.getAccuracy()) + ",";
//            sharedpreferences.edit().putString("accuracy", accuracy).commit();
//            Log.d("LocationService", String.valueOf(location.getLatitude()) + " location accuracy " + location.getAccuracy());
            broadcastLocation(location);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
        // startService(new Intent(LocationService.this,LocationService.class));
    }

    //call this in onPause method
    protected void stopLocationUpdates() {
        mFusedLocationProviderClient.removeLocationUpdates(locationCallBcak);
        Log.d("startLocationUpdates", "Location update stopped .......................");
    }

    protected void startLocationUpdates() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("checkSelfPermission", "checkSelfPermission not granted.");

            return;
        }
        mFusedLocationProviderClient.requestLocationUpdates(
                mLocationRequest, locationCallBcak, null);
        Log.d("startLocationUpdates", "Location update started ..............: ");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("onConnected", "onConnected");
        startLocationUpdates();

        sharedpreferences.edit().putString("accuracy", String.valueOf("")).commit();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public Handler getUpadte() {

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startLocationUpdates();
            }
        }, 2000);
        return handler;
    }

    public void setOnLocationUpdateListener(OnLocationUpdateListener onLocationUpdateListener) {
        ///this.onLocationUpdateListener = onLocationUpdateListener;
    }

    private void broadcastLocation(Location location) {
        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", location.getLatitude());
        bundle.putDouble("longitude", location.getLongitude());
        bundle.putFloat("accuracy", location.getAccuracy());
        Intent intent1 = new Intent();
        intent1.setAction(BROADCAST_LOCATIONS);
        intent1.putExtras(bundle);
        sendBroadcast(intent1);
    }

    @Override
    public void onGpsStatusChanged(int event) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        GpsStatus status = mService.getGpsStatus(null);
        switch (event) {
            case GpsStatus.GPS_EVENT_STARTED:
                Common.Log.i("onGpsStatusChanged: GPS_EVENT_STARTED");
                break;
            case GpsStatus.GPS_EVENT_STOPPED:
                Common.Log.i("onGpsStatusChanged: GPS_EVENT_STOPPED");
               /* if (mGpsFixed) {
                    mGpsFixed = false;
                    notifyGpsFixChanged();
                }*/
                break;
            case GpsStatus.GPS_EVENT_FIRST_FIX:
                Common.Log.i("onGpsStatusChanged: GPS_EVENT_FIRST_FIX");
              /*  mGpsFixed = true;
                notifyGpsFixChanged();*/

                break;
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                int maxSatellites = status.getMaxSatellites();
                Common.Log.i("onGpsStatusChanged: GPS_EVENT_SATELLITE_STATUS " + maxSatellites);
                Iterator<GpsSatellite> it = status.getSatellites().iterator();

                int count = 0;
                while (it.hasNext() && count <= maxSatellites) {
                    GpsSatellite s = it.next();
                    Log.i("gjh", "PNR：" + s.getPrn() + "----------");
                    Log.i("gjh", "SNR：" + s.getSnr() + ", usedInFix:" + s.usedInFix());
                    //  Log.i("gjh","Elevation：" + s.getElevation());
                    //  Log.i("gjh","Azimuth ：" + s.getAzimuth());


                    count++;
                }
              /*  mGpsFixed = true;
                notifyGpsFixChanged();*/

                break;
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("on", "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;

    }


}
