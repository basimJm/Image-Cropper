package com.bj.image_cropper.compose_navigation.route

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data class Home(
        val imagePath: String? = null
    ) : Routes {
        companion object {
            fun from(savedStateHandle: SavedStateHandle) =
                savedStateHandle.toRoute<Home>()
        }
    }

    @Serializable
    data class ImageEditor(val imageUri: String) : Routes {
        val uriObject: Uri
            get() = imageUri.toUri()

        companion object {
            fun from(savedStateHandle: SavedStateHandle) =
                savedStateHandle.toRoute<ImageEditor>()
        }
    }
}