package com.bj.image_cropper.feature.cropper.presentation.controller.contract

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import com.bj.image_cropper.feature.cropper.presentation.ui.component.CropError
import com.bj.image_cropper.feature.cropper.presentation.ui.component.CropState
import com.bj.image_cropper.feature.cropper.presentation.ui.component.ImageCropper

interface ImageCropperContract {
    data class UiState(
        val imageUri: Uri? = null,
        val imageBitmap: ImageBitmap? = null,
        val cropState: CropState? = null,
        val imageCropper: ImageCropper = ImageCropper(),
        val error: CropError? = null
    )

    sealed interface UiAction {
        data class OnCropDone(val bitmapImage: ImageBitmap) : UiAction
        data object OnBackPressed : UiAction
    }

    sealed interface SideEffect {
        data class BackToHomeScreen(val bitmapImage: ImageBitmap?) : SideEffect
    }
}