package com.bj.image_cropper.compose_navigation.route

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object Home : Routes

    @Serializable
    data class ImageEditor(val uri: String) : Routes {
        val uriObject: Uri
            get() = uri.toUri()

        companion object {
            fun from(savedStateHandle: SavedStateHandle) =
                savedStateHandle.toRoute<ImageEditor>()
        }
    }
}