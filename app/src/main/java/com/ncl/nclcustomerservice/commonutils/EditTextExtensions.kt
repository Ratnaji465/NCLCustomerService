package com.ncl.nclcustomerservice.commonutils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


fun EditText.onTextChange(listener: (Editable?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            listener(p0)
        }

    })
}