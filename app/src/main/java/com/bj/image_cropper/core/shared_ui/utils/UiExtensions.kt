package com.bj.image_cropper.core.shared_ui.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast

fun Context.showMessage(
    resId: Int,
) {
    val activity = this as Activity
    val message = this.getString(resId)
    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}