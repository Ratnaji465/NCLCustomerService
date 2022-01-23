package com.ncl.nclcustomerservice.checkinout;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.ncl.nclcustomerservice.commonutils.Common;


/**
 * Created by user on 18-07-2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements PermissionUtil.PermissionAskListener {


    public final int GPS_REQUEST_CODE = 1;
    public final int WRITE_REQUEST_CODE = 2;
//    DatabaseHandler db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        db = DatabaseHandler.getDatabase(this);
        Log.d("activity", this.getClass().getSimpleName());
        if (!this.getClass().getSimpleName().equalsIgnoreCase("SplashScreen")) {
            PermissionUtil.checkPermission(this, this, Manifest.permission.ACCESS_FINE_LOCATION, this, GPS_REQUEST_CODE);

        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


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
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                PermissionUtil.checkPermission(this, this, Manifest.permission.WRITE_EXTERNAL_STORAGE, this, WRITE_REQUEST_CODE);
                return;


            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public boolean isRM() {
        if (Common.getRoleIdFromSP(this) == 2)
            return true;

        return false;
    }

    public boolean isAM() {
        if (Common.getRoleIdFromSP(this) == 4)
            return true;

        return false;
    }

    public boolean isMO() {
        if (Common.getRoleIdFromSP(this) == 3)
            return true;

        return false;
    }

//    public boolean isADMIN() {
//        if (Common.getRoleIdFromSP(this) == Constants.Roles.ADMIN)
//            return true;
//
//        return false;
//    }

//    public List<Company> getCompaniesByEmployee() {
//        return db.commonDao().getCompaniesByEmployee(Common.getUserIdFromSP(this), "1");
//    }
}
