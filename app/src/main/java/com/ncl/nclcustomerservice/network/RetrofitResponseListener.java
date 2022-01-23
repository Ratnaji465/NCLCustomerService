package com.ncl.nclcustomerservice.network;

import android.app.ProgressDialog;

import com.ncl.nclcustomerservice.object.ApiRequestController;
import com.ncl.nclcustomerservice.object.ApiResponseController;


public interface RetrofitResponseListener {

    void onResponseSuccess(ApiResponseController objectResponse, ApiRequestController objectRequest, ProgressDialog progressDialog);

}
