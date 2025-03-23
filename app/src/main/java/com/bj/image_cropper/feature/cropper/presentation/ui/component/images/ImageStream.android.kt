package com.bj.image_cropper.feature.cropper.presentation.ui.component.images

import android.content.Context
import android.net.Uri
import java.io.InputStream

internal fun interface ImageStream {
    fun openStream(): InputStream?
}

internal suspend fun Uri.toImageSrc(context: Context) =
    ImageStreamSrc(UriImageStream(this, context))

internal data class UriImageStream(val uri: Uri, val context: Context) : ImageStream {
    override fun openStream(): InputStream? = context.contentResolver.openInputStream(uri)
}

