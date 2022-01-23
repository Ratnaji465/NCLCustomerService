package com.ncl.nclcustomerservice.network;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import androidx.legacy.content.WakefulBroadcastReceiver;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ncl.nclcustomerservice.application.MyApplication;
import com.ncl.nclcustomerservice.commonutils.Common;
import com.ncl.nclcustomerservice.commonutils.Constants;
import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RetrofitRequestController {
    private final RetrofitResponseListener retrofitResponseListener;
    private Activity activity;
    private Context context;
    private ProgressDialog progressDialog;

    public RetrofitRequestController(RetrofitResponseListener retrofitResponseListener) {

        this.retrofitResponseListener = retrofitResponseListener;

        if (retrofitResponseListener instanceof Activity) {
            this.context = (Context) retrofitResponseListener;
            this.activity = (Activity) retrofitResponseListener;
        } else if (retrofitResponseListener instanceof Fragment) {
            this.context = ((Fragment) retrofitResponseListener).getActivity();
            this.activity = ((Fragment) retrofitResponseListener).getActivity();

        } else if (retrofitResponseListener instanceof androidx.fragment.app.Fragment) {
            this.context = ((androidx.fragment.app.Fragment) retrofitResponseListener).getActivity();
            this.activity = ((androidx.fragment.app.Fragment) retrofitResponseListener).getActivity();

        } else if (retrofitResponseListener instanceof WakefulBroadcastReceiver) {
            this.context = (Context) retrofitResponseListener;
            this.activity = (Activity) retrofitResponseListener;
        } else if (retrofitResponseListener instanceof Service) {
            this.context = (Context) retrofitResponseListener;
        }

    }


    public void sendRequest(String requestName, final Object obj, boolean showProgressDialog) {

        if (!Common.haveInternet(context)) {

            Common.customToast(context, Constants.INTERNET_UNABLEABLE);

            return;

        }
        if (showProgressDialog) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog = Common.showProgressDialog(context);
                    progressDialog.setCancelable(false);
                }
            });
        }

        final ApiRequestController apiRequestController = new ApiRequestController();
        apiRequestController.requesterid = Common.getUserIdFromSP(context);
        //  apiRequestController.requesterid =36;
        apiRequestController.requestname = requestName;
        apiRequestController.requestparameters = obj;

        //   Common.Log.i("request :"+ new Gson().toJson(apiRequestController));


        MyApplication.getInstance().getAPIInterface().callPost(Constants.API, apiRequestController).enqueue(new Callback<ApiResponseController>() {
            @Override
            public void onResponse(Call<ApiResponseController> call, Response<ApiResponseController> response) {

                try {
                    Common.Log.i("Response: " + new Gson().toJson(response.body()));
                    if (response.body() == null) {
                        Common.dismissProgressDialog(progressDialog);
                        Toast.makeText(context, "Intenal Server Error", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (response.body().code != Constants.SUCCESS) {
                        Common.dismissProgressDialog(progressDialog);
                        Toast.makeText(context, response.body().message, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    retrofitResponseListener.onResponseSuccess(response.body(), apiRequestController, progressDialog);
                    // Common.Log.i("Response: absajk");
                } catch (Exception e) {
                    Common.disPlayExpection(e, progressDialog);
                }
            }

            @Override
            public void onFailure(Call<ApiResponseController> call, Throwable t) {
                Common.Log.i("retrofit " + t.toString());
                Common.dismissProgressDialog(progressDialog);
            }
        });
    }

    public void sendRequest(String requestName, final Object obj, boolean showProgressDialog, APIInterface apiInterface) {

        if (!Common.haveInternet(context)) {

            Common.customToast(context, Constants.INTERNET_UNABLEABLE);

            return;

        }
        if (showProgressDialog) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog = Common.showProgressDialog(context);
                    progressDialog.setCancelable(false);
                }
            });
        }

        final ApiRequestController apiRequestController = new ApiRequestController();
        apiRequestController.requesterid = Common.getUserIdFromSP(context);
        //  apiRequestController.requesterid =36;
        apiRequestController.requestname = requestName;
        apiRequestController.requestparameters = obj;

        //   Common.Log.i("request :"+ new Gson().toJson(apiRequestController));


        apiInterface.callPost(Constants.API, apiRequestController).enqueue(new Callback<ApiResponseController>() {
            @Override
            public void onResponse(Call<ApiResponseController> call, Response<ApiResponseController> response) {

                try {
                    Common.Log.i("Response: " + new Gson().toJson(response.body()));
                    if (response.body() == null) {
                        Common.dismissProgressDialog(progressDialog);
                        Toast.makeText(context, "Intenal Server Error", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (response.body().code != Constants.SUCCESS) {
                        Common.dismissProgressDialog(progressDialog);
                        Toast.makeText(context, response.body().message, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    retrofitResponseListener.onResponseSuccess(response.body(), apiRequestController, progressDialog);
                    // Common.Log.i("Response: absajk");
                } catch (Exception e) {
                    Common.disPlayExpection(e, progressDialog);
                }
            }

            @Override
            public void onFailure(Call<ApiResponseController> call, Throwable t) {
                Common.Log.i("retrofit " + t.toString());
                Common.dismissProgressDialog(progressDialog);
            }
        });
    }


    public void offlineRequest(String requestName, final Object obj, ApiResponseController apiResponseController, boolean showProgressDialog) {

        if (!Common.haveInternet(context)) {

            Common.customToast(context, Constants.INTERNET_UNABLEABLE);

            return;

        }


        if (showProgressDialog) {

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog = Common.showProgressDialog(context);
                    progressDialog.setCancelable(false);
                }
            });


        }

        final ApiRequestController apiRequestController = new ApiRequestController();
        apiRequestController.requesterid = Common.getUserIdFromSP(context);
        // apiRequestController.requesterid =-1;
        apiRequestController.requestname = requestName;
        apiRequestController.requestparameters = obj;

        Common.Log.i("request offline :" + new Gson().toJson(apiRequestController));

        retrofitResponseListener.onResponseSuccess(apiResponseController, apiRequestController, progressDialog);

    }

}
