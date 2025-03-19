package com.bj.image_cropper.feature.image_editor.controller.vm

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bj.image_cropper.compose_navigation.route.Routes
import com.bj.image_cropper.core.mvi.MVI
import com.bj.image_cropper.core.mvi.mvi
import com.bj.image_cropper.feature.image_editor.controller.contract.ImageEditorContract.SideEffect
import com.bj.image_cropper.feature.image_editor.controller.contract.ImageEditorContract.SideEffect.BackToHomeScreen
import com.bj.image_cropper.feature.image_editor.controller.contract.ImageEditorContract.UiAction
import com.bj.image_cropper.feature.image_editor.controller.contract.ImageEditorContract.UiState
import com.bj.image_cropper.feature.image_editor.ui.component.CropError
import com.bj.image_cropper.feature.image_editor.ui.component.CropResult
import com.bj.image_cropper.feature.image_editor.ui.component.crop
import kotlinx.coroutines.launch

class ImageEditorViewModel(savedStateHandle: SavedStateHandle, private val app: Application) :
    ViewModel(),
    MVI<UiState, UiAction, SideEffect> by mvi(UiState()) {
    private val passedUri = Routes.ImageEditor.from(savedStateHandle).uriObject

    init {
        updateState {
            copy(
                imageUri = passedUri,
            )
        }
        setSelectedImage(uri = passedUri)
    }

    override fun onEvent(event: UiAction) {

        viewModelScope.launch {
            when (event) {
                is UiAction.OnBackPressed -> {
                    emitSideEffect(BackToHomeScreen(null, uiState.value.imageUri))
                }

                is UiAction.OnCropDone -> {
                    emitSideEffect(BackToHomeScreen(event.bitmapImage, null))
                }
            }
        }
    }

    private fun setSelectedImage(uri: Uri) {
        viewModelScope.launch {
            when (val result = uiState.value.imageCropper.crop(uri, app)) {
                CropResult.Cancelled -> {}
                is CropError -> {
                    updateState {
                        copy(
                            error = result
                        )
                    }
                }

                is CropResult.Success -> {
                    onEvent(UiAction.OnCropDone(result.bitmap))
                    updateState {
                        copy(
                            bitmapImage = result.bitmap
                        )
                    }
                }
            }
        }
    }
}