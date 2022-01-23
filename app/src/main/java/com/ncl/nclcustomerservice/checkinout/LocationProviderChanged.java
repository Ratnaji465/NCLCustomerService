package com.ncl.nclcustomerservice.checkinout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ncl.nclcustomerservice.commonutils.Common;


/**
 * Created by suprasoft on 1/27/2018.
 */

public class LocationProviderChanged extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("LocationProviderChanged","onReceive......");
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {

            if (Common.isMyServiceRunning(context, LocationUpdatesService.class)) {
                if (!Common.isGpsEnabled(context)){
                    saveGpsStatus(context,Common.currentTimeFromMS(System.currentTimeMillis())+" "+"OFF");

                }else{
                    saveGpsStatus(context,Common.currentTimeFromMS(System.currentTimeMillis())+" "+"ON");
                }

            }

        }

    }

    public static void saveGpsStatus(Context context,String time){
        Common.getDefaultSP(context).edit().putString("Location_onReceiveCalled",getGpsStatus(context)+" ! "+time).commit();

    }
    public static String getGpsStatus(Context context){
        return Common.getDefaultSP(context).getString("Location_onReceiveCalled","");

    }
}
