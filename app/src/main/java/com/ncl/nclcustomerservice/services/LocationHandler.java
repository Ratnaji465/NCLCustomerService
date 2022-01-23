package com.ncl.nclcustomerservice.services;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.ncl.nclcustomerservice.commonutils.Common;


/**
 * Created by suprasoft on 2/1/2018.
 */

public class LocationHandler implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private OnLocationUpdateListener onLocationUpdateListener;

    public LocationHandler(Context mContext) {
        this.mContext = mContext;
        createLocationRequest();
        buildGoogleApiClient();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void startLocationUpdates(Context mContext) {
        if (ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Common.Log.i("startLocationUpdates..");
    }

    public void stopLocationUpdate(Context mContext) {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        Common.Log.i("stoppedLocationUpdates..LH");

    }

    public boolean isGoogleApiClient() {
        return mGoogleApiClient != null && mGoogleApiClient.isConnected();
    }

    //other new Methods but not using right now..
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);//set the interval in which you want to get locations
        mLocationRequest.setFastestInterval(3000);//if a location is available sooner you can get it (i.e. another app is using the location services)
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        onLocationUpdateListener.onGoogleClientConnected(mGoogleApiClient);
        //startLocationUpdates(mContext);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mGoogleApiClient.isConnected() && onLocationUpdateListener != null) {
            onLocationUpdateListener.onLocationChange(location);
            Common.Log.i("AK H " + location.toString());
        }
    }

    public void setOnLocationUpdateListener(OnLocationUpdateListener onLocationUpdateListener) {
        this.onLocationUpdateListener = onLocationUpdateListener;
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    public LocationRequest getLocationRequest() {

        return mLocationRequest;
    }

    public boolean disconnectGoogleClient() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            return true;
        }
        return false;
    }

}