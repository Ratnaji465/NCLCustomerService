package com.ncl.nclcustomerservice.commonutils

import android.app.Activity
import android.widget.Toast


fun Activity.toast(message: String?) {
    message?.let { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
}

fun <T> Activity.getArguments(): T? {
    return intent.extras?.get("args")?.let {
        it as T
    }
}