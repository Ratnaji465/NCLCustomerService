package com.ncl.nclcustomerservice.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.ncl.nclcustomerservice.R;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.customviews.CustomerAlertDialog3;
import com.ncl.nclcustomerservice.customviews.DialogClickListener;
import com.ncl.nclcustomerservice.network.RetrofitResponseListener;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;
import com.ncl.nclcustomerservice.object.AppVersionResVo;

public class SplashActivity extends AppCompatActivity implements RetrofitResponseListener {

    private String currentVersion;
    private static int SPLASH_TIME_OUT = 3000;
    private CustomerAlertDialog3 customerAlertDialog3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        startService(new Intent(this, BackgroundService.class));
        //  showNotificationMessage(this,"title", "message", new Intent());
        if (!Common.haveInternet(this)) {
            new Handler().postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void run() {

                    customerAlertDialog3 = new CustomerAlertDialog3(SplashActivity.this, "Network Error!", Common.INTERNET_UNABLEABLE, "Exit", new DialogClickListener() {
                        @Override
                        public void onCancelClick() {
                            customerAlertDialog3.dismiss();
                            // Common.quit();
                        }

                        @Override
                        public void onOkClick() {
//                            Common.quit(SplashActivity.this);
                        }
                    });

                    customerAlertDialog3.show();
                }
            }, 100);

            return;

        }
        getCurrentVersion();
        /*new Thread() {

            @Override
            public void run() {
                super.run();
                try {
                    sleep(1000);
//                    int roleId = Common.getUserRoleFromSP(getApplicationContext());
                    Intent homeIntent;
                    if (Common.getUserIdFromSP(SplashActivity.this) == 0) {
                        homeIntent = new Intent(getApplicationContext(), LoginActivity.class);
//                        homeIntent.putExtra("role", roleId);
                    } else {
                        homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                    }
                    startActivity(homeIntent);
                    finish();
                } catch (Exception e) {

                }
            }
        }.start();*/
    }

    private void getCurrentVersion() {


        PackageManager pm = this.getPackageManager();

        PackageInfo pInfo = null;

        try {
            pInfo = pm.getPackageInfo(this.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        currentVersion = pInfo.versionName;

        System.out.println("? - My local app version====>>>>>" + currentVersion);

//        Common.customToast(this,"currentVersion : "+currentVersion);


        if (!Common.haveInternet(getApplicationContext())) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent homeIntent;
                    if (Common.getUserIdFromSP(SplashActivity.this) == 0) {
                        homeIntent = new Intent(getApplicationContext(), LoginActivity.class);
//                        homeIntent.putExtra("role", roleId);
                    } else {
                        homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                    }
                    startActivity(homeIntent);
                    finish();
                }
            }, SPLASH_TIME_OUT);


        } else {

//            setContentView(R.layout.splash);
//            new RetrofitRequestController(this).sendRequest(Constants.RequestNames.APP_CURRENT_VERSION, "", false);
            Intent homeIntent;
            if (Common.getUserIdFromSP(SplashActivity.this) == 0) {
                homeIntent = new Intent(getApplicationContext(), LoginActivity.class);
//                        homeIntent.putExtra("role", roleId);
            } else {
                homeIntent = new Intent(getApplicationContext(), MainActivity.class);
            }
            startActivity(homeIntent);
            finish();

        }


    }

    @Override
    public void onResponseSuccess(ApiResponseController objectResponse, ApiRequestController objectRequest, ProgressDialog progressDialog) {
        try {
            AppVersionResVo appVersionResVo = Common.getSpecificDataObject(objectResponse.result, AppVersionResVo.class);
            if (appVersionResVo != null) {
                String appVersionName = appVersionResVo.appVersionName;
                if (appVersionName != null && !appVersionName.equalsIgnoreCase(currentVersion)) {
                    showUpdateDialog();
                } else {
                    Intent homeIntent;
                    if (Common.getUserIdFromSP(SplashActivity.this) == 0) {
                        homeIntent = new Intent(getApplicationContext(), LoginActivity.class);
//                        homeIntent.putExtra("role", roleId);
                    } else {
                        homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                    }
                    startActivity(homeIntent);
                    finish();
                }
            }
        } catch (Exception e) {
        }
        Common.dismissProgressDialog(progressDialog);
    }

    private void showUpdateDialog() {

        //Common.getDefaultSP(this).edit().putBoolean(Constants.SharedPreferencesKeys.LOGIN_STATUS,false);


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(Common.getStringResourceText(R.string.a_new_update_is_available));
        builder.setPositiveButton(Common.getStringResourceText(R.string.update), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.ncl.buildtek"));


                startActivity(intent);


            }
        });


        builder.setCancelable(false);
        builder.show();
    }

//    private void showNotificationMessage(Context myFirebaseMessagingService1, String title, String message, Intent intent) {
//        NotificationUtils notificationUtils = new NotificationUtils(myFirebaseMessagingService1);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        notificationUtils.showNotificationMessage(title, message, intent);
//    }
}
