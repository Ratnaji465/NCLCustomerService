package com.ncl.nclcustomerservice.checkinout;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;

/**
 * Created by suprasoft on 4/20/2018.
 */

public class PermissionUtil {
    /*
 * Check if version is marshmallow and above.
 * Used in deciding to ask runtime permission
 * */
    public static boolean shouldAskPermission() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }
    private static boolean shouldAskPermission(Context context, String permission){
        if (shouldAskPermission()) {
            int permissionResult = ActivityCompat.checkSelfPermission(context, permission);
            if (permissionResult != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }
    public static void checkPermission(Context context, Activity activity,String permission, PermissionAskListener listener,int requestCode){
/*
        * If permission is not granted
        * */
        if (shouldAskPermission(context, permission)){
/*
            * If permission denied previously
            * */
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)) {
                listener.onPermissionPreviouslyDenied(permission,requestCode);
            } else {
                /*
                * Permission denied or first time requested
                * */
               // if (PreferencesUtil.isFirstTimeAskingPermission(context, permission)) {
                //    PreferencesUtil.firstTimeAskingPermission(context, permission, false);
                    listener.onNeedPermission(permission,requestCode);
                /*} else {
                    *//*
                    * Handle the feature without permission or ask user to manually allow permission
                    * *//*
                    listener.onPermissionDisabled();
                }*/
            }
        } else {
            listener.onPermissionGranted(permission,requestCode);
        }
    }
    /*
        * Callback on various cases on checking permission
        *
        * 1.  Below M, runtime permission not needed. In that case onPermissionGranted() would be called.
        *     If permission is already granted, onPermissionGranted() would be called.
        *
        * 2.  Above M, if the permission is being asked first time onNeedPermission() would be called.
        *
        * 3.  Above M, if the permission is previously asked but not granted, onPermissionPreviouslyDenied()
        *     would be called.
        *
        * 4.  Above M, if the permission is disabled by device policy or the user checked "Never ask again"
        *     check box on previous request permission, onPermissionDisabled() would be called.
        * */
    public interface PermissionAskListener {
        /*
                * Callback to ask permission
                * */
        void onNeedPermission(String permission, int requestCode);
        /*
                * Callback on permission denied
                * */
        void onPermissionPreviouslyDenied(String permission, int requestCode);
        /*
                * Callback on permission "Never show again" checked and denied
                * */
        void onPermissionDisabled(String permission, int requestCode);
        /*
                * Callback on permission granted
                * */
        void onPermissionGranted(String permission, int requestCode);
    }
}


