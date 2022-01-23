package com.ncl.nclcustomerservice.object;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import android.util.Log;

/**
 * Created by sowmy on 8/29/2018.
 */

public class GpsTracker extends Service implements LocationListener{
    private final Context mContext;

    boolean isGPSEnabled = false;

    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;

    Location location;
    double latitude;
    double longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES =10; //10 meters

    private static final long MIN_TIME_BW_UPDATES =1000*60*1 ; //1 minute

    protected LocationManager locationManager;

    GpsTracker(Context mContext) {
        this.mContext = mContext;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager =(LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            isGPSEnabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            isNetworkEnabled =locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled){

            }else {
                this.canGetLocation =true;

                if (isNetworkEnabled){
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions((Activity)mContext, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES,this);

                    Log.d("Network","Network");
                    if (locationManager !=null){
                        location =locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location !=null){
                            latitude = location.getLatitude();
                            longitude =location.getLongitude();
                        }
                    }
                }

                if (isGPSEnabled){
                      if (location == null){

                          if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                              ActivityCompat.requestPermissions((Activity)mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},101);
                          }
                          locationManager.requestLocationUpdates(
                                  LocationManager.NETWORK_PROVIDER,
                                  MIN_TIME_BW_UPDATES,
                                  MIN_DISTANCE_CHANGE_FOR_UPDATES,this);

                          Log.d("GPS Enabled","GPS Enabled");
                          if (locationManager !=null){
                              location =locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                              if (location !=null){
                                  latitude =location.getLatitude();
                                  longitude =location.getLongitude();
                              }
                          }
                      }
                }
            }

        }catch (Exception e){
            e.printStackTrace();

        }

        return location;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public boolean canGetLocation() {

        return canGetLocation;
    }

    public double getLatitude() {
        if (location != null){
            latitude =location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }
}
