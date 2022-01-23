package com.ncl.nclcustomerservice.checkinout;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by User on 10/15/2018.
 */

public class MarkerData {
    LatLng latLng;
    String str_name = "";
    String str_address = "";

    public MarkerData(LatLng latLng, String str_name, String str_address) {
        this.latLng = latLng;
        this.str_name = str_name;
        this.str_address = str_address;
    }
}
