package com.bj.image_cropper.feature.home.presentation.controller.contract

import android.net.Uri
import com.bj.image_cropper.R

interface HomeContract {
    data class UiState(
        val selectedImageUri: Uri? = null,
        val buttonTextResource: Int = R.string.selected_image_from_gallery
    )

    sealed interface UiAction {
        data class OnImageSelected(val uri: Uri) : UiAction
        data object OnNavigateToImageEditorScreen : UiAction
    }

    sealed interface SideEffect {
        data class ShowMessageFromRes(val resId: Int) : SideEffect
        data class NavigateToImageEditorScreen(val uri: Uri) : SideEffect
    }
}