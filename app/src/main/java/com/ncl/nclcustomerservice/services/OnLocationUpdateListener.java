package com.ncl.nclcustomerservice.services;

import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;


/**
 * Created by suprasoft on 2/1/2018.
 */

public interface OnLocationUpdateListener {
    void onLocationChange(Location location);
    void onGoogleClientConnected(GoogleApiClient googleApiClient);
}
