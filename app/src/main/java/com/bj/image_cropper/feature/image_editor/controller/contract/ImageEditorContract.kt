package com.bj.image_cropper.feature.image_editor.controller.contract

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import com.bj.image_cropper.feature.image_editor.ui.component.CropError
import com.bj.image_cropper.feature.image_editor.ui.component.CropState
import com.bj.image_cropper.feature.image_editor.ui.component.ImageCropper

interface ImageEditorContract {
    data class UiState(
        val imageUri: Uri? = null,
        val bitmapImage: ImageBitmap? = null,
        val cropState: CropState? = null,
        val imageCropper: ImageCropper = ImageCropper(),
        val error: CropError? = null
    )

    sealed interface UiAction {

    }

    sealed interface SideEffect {

    }
}