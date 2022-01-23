package com.ncl.nclcustomerservice.abstractclasses;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import android.util.Log;

import com.ncl.nclcustomerservice.application.BackgroundService;
import com.ncl.nclcustomerservice.application.MyApplication;
import com.ncl.nclcustomerservice.checkinout.BaseActivity;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.commonutils.PermissionUtil;
import com.ncl.nclcustomerservice.customviews.CustomerAlertDialog3;
import com.ncl.nclcustomerservice.customviews.DialogClickListener;

//import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.ncl.nclcustomerservice.commonutils.Common.setSettingsAutomaticDateTimeIfNeeded;


public abstract class NetworkChangeListenerActivity extends BaseActivity implements PermissionUtil.PermissionAskListener {

    public static final String NETWORK_CHANGE_RECEIVED = MyApplication.getInstance().getPackageName() + ".NETWORK_CHANGED";
    private CustomerAlertDialog3 customerAlertDialog3;
    public final int GPS_REQUEST_CODE = 1;
    public final int WRITE_REQUEST_CODE = 2;
    private CustomerAlertDialog3 customerAlertDialog4;

   /* @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("activity", this.getClass().getSimpleName());
        if (!this.getClass().getSimpleName().equalsIgnoreCase("SplashActivity")) {
            PermissionUtil.checkPermission(this, this, Manifest.permission.ACCESS_FINE_LOCATION, this, GPS_REQUEST_CODE);

        }
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                //Migrate to Setting write permission screen.
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 10);
                return;
            }else{
                setSettingsAutomaticDateTimeIfNeeded(this);
            }

        }*/

        registerReceiver(broadcastReceiver, new IntentFilter(NETWORK_CHANGE_RECEIVED));

        customerAlertDialog3 = new CustomerAlertDialog3(NetworkChangeListenerActivity.this, "Network Error!", Common.INTERNET_UNABLEABLE, "Exit", new DialogClickListener() {
            @Override
            public void onCancelClick() {
                //  customerAlertDialog3.dismiss();
                // Common.quit();
            }

            @Override
            public void onOkClick() {

                Common.quit(NetworkChangeListenerActivity.this);
            }
        });
        customerAlertDialog4 = new CustomerAlertDialog3(NetworkChangeListenerActivity.this, "Date and Time Error!", "Please Select Network provide date & time ", "Exit", new DialogClickListener() {
            @Override
            public void onCancelClick() {
                //  customerAlertDialog3.dismiss();
                // Common.quit();
            }

            @Override
            public void onOkClick() {

                Common.quit(NetworkChangeListenerActivity.this);
            }
        },false);

        if ( !setSettingsAutomaticDateTimeIfNeeded(this)){
            if (!isFinishing() && customerAlertDialog4 != null) {
                customerAlertDialog4.show();
            }
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Common.Log.i("Inside BroadcastReceiver - onReceive");

            Common.Log.i("? - intent.getIntExtra(Constants.KEY_1, 0) : " + intent.getIntExtra(Constants.KEY_1, 0));

            if (intent.getIntExtra(Constants.KEY_1, 0) == 0) {
                if (!isFinishing() && customerAlertDialog3 != null) {
                    customerAlertDialog3.show();
                }
                onInternetDisconnected();

            } else {
                if (customerAlertDialog3 != null && customerAlertDialog3.isShowing()) {
                    customerAlertDialog3.dismiss();
                }
                onInternetConnected();
                Common.startService(getApplicationContext(), BackgroundService.class);

            }


        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (broadcastReceiver != null)
                unregisterReceiver(broadcastReceiver);
        }catch (Exception e){}
    }


    protected abstract void onInternetConnected();

    protected abstract void onInternetDisconnected();

    public void checkWriteExternalStoragePermission(Context context, String permission, int request_code) {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{permission},
                request_code);
    }


    @Override
    public void onNeedPermission(String permission, int requestCode) {
        Log.d("onNeedPermission", "onNeedPermission..");
        checkWriteExternalStoragePermission(this, permission, requestCode);

    }

    @Override
    public void onPermissionPreviouslyDenied(String permission, int requestCode) {
        Log.d("onNeedPermission", "onPermissionPreviouslyDenied..");
        checkWriteExternalStoragePermission(this, permission, requestCode);
    }

    @Override
    public void onPermissionDisabled(String permission, int requestCode) {
        Log.d("onNeedPermission", "onPermissionDisabled..");
    }

    @Override
    public void onPermissionGranted(String permission, int requestCode) {
        Log.d("onNeedPermission", "onPermissionGranted..");
        if (requestCode == GPS_REQUEST_CODE) {
            PermissionUtil.checkPermission(this, this, Manifest.permission.WRITE_EXTERNAL_STORAGE, this, WRITE_REQUEST_CODE);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Common.Log.i("Permisiion.... " + requestCode + " \n " + permissions.length + " \n" + grantResults);
        switch (requestCode) {

            case GPS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                PermissionUtil.checkPermission(this, this, Manifest.permission.WRITE_EXTERNAL_STORAGE, this, WRITE_REQUEST_CODE);
                return;


            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.System.canWrite(this)) {
            setSettingsAutomaticDateTimeIfNeeded(this);
            recreate();
        }
    }


}
