package com.bj.image_cropper.feature.home.presentation.controller.contract

import android.net.Uri
import com.bj.image_cropper.R

interface HomeContract {
    data class UiState(
        val imageUri: Uri? = null,
        val imagePath: String? = null,
        val buttonTextResource: Int = R.string.selected_image_from_gallery
    )

    sealed interface UiAction {
        data class OnImageSelected(val uri: Uri) : UiAction
        data object OnNavigateToImageCropperScreen : UiAction
    }

    sealed interface SideEffect {
        data class ShowMessageFromRes(val resId: Int) : SideEffect
        data class NavigateToImageCropperScreen(val uri: Uri) : SideEffect
    }
}