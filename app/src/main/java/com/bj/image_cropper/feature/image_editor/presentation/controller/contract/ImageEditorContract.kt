package com.bj.image_cropper.feature.image_editor.presentation.controller.contract

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.CropError
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.CropState
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.ImageCropper

interface ImageEditorContract {
    data class UiState(
        val imageUri: Uri? = null,
        val bitmapImage: ImageBitmap? = null,
        val cropState: CropState? = null,
        val imageCropper: ImageCropper = ImageCropper(),
        val error: CropError? = null
    )

    sealed interface UiAction {
        data class OnCropDone(val bitmapImage: ImageBitmap) : UiAction
        data class OnBackPressed(val imageUri: Uri) : UiAction
    }

    sealed interface SideEffect {
        data class BackToHomeScreen(val bitmapImage: ImageBitmap?, val imageUri: Uri?) : SideEffect
    }
}