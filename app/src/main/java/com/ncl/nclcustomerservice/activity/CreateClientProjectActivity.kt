package com.ncl.nclcustomerservice.activity

import android.app.ProgressDialog
import com.ncl.nclcustomerservice.abstractclasses.NetworkChangeListenerActivity
import com.ncl.nclcustomerservice.network.RetrofitResponseListener
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.`object`.ApiRequestController
import com.ncl.nclcustomerservice.`object`.ApiResponseController
import com.ncl.nclcustomerservice.databinding.ActivityCreateClientprojectBinding

class CreateClientProjectActivity : NetworkChangeListenerActivity(), RetrofitResponseListener {
    private lateinit var binding: ActivityCreateClientprojectBinding
    override fun onInternetConnected() {}
    override fun onInternetDisconnected() {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_clientproject)

    }

    override fun onResponseSuccess(objectResponse: ApiResponseController, objectRequest: ApiRequestController, progressDialog: ProgressDialog) {}
}