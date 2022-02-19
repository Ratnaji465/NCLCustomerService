package com.ncl.nclcustomerservice.commonutils

import android.view.View

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.showOrHide(isShowing: Boolean) {
    this.visibility = if (isShowing) View.VISIBLE else View.GONE
}