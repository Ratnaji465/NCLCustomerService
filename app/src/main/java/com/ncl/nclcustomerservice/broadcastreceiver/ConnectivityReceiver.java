package com.ncl.nclcustomerservice.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ncl.nclcustomerservice.abstractclasses.NetworkChangeListenerActivity;
import com.ncl.nclcustomerservice.abstractclasses.NetworkUtil;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.customviews.CustomerAlertDialog3;


public class ConnectivityReceiver extends BroadcastReceiver {

    public static ConnectivityReceiverListener connectivityReceiverListener;
    SharedPreferences sharedpreferences;
    boolean isConnected;
    private Context context;
    private CustomerAlertDialog3 customerAlertDialog3;

    public ConnectivityReceiver() {
        super();
    }

    @Override
    public void onReceive(final Context context, Intent arg1) {
        this.context = context;
//        Log.d("ConnectivityManager", String.valueOf("ConnectivityManager..."));
Common.Log.i("Connectivity Changed");
        int status = NetworkUtil.getConnectivityStatus(context);

        Intent broadcastIntent = new Intent(NetworkChangeListenerActivity.NETWORK_CHANGE_RECEIVED);

        broadcastIntent.putExtra(Constants.KEY_1,status);

        context.sendBroadcast(broadcastIntent);


        if(status == 0)
        {
            Common.Log.i("Inside NCR - "+ Constants.NO_INTERNET_CONNECTION_MESSAGE);
        }
        else
        {
            Common.Log.i("Inside NCR - Internet Connected.");
        }

    }


    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }


}