package com.bj.image_cropper.feature.cropper.utils

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.bj.image_cropper.R
import java.io.File
import java.io.FileOutputStream

object ImageCropperUtils {
    fun saveImageBitmapToCache(
        imageBitmap: ImageBitmap?,
        context: Context
    ): String? {
        imageBitmap?.let {
            val bitmap = imageBitmap.asAndroidBitmap()
            val file = File(
                context.cacheDir,
                context.getString(R.string.image_path)
            )
            FileOutputStream(file).use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
            return file.absolutePath
        } ?: return null
    }
}