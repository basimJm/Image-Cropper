package com.bj.image_cropper.feature.image_editor.controller.contract

import android.net.Uri

interface ImageEditorContract {
    data class UiState(
        val imageUri: Uri? = null,
    )

    sealed interface UiAction {

    }

    sealed interface SideEffect {

    }
}