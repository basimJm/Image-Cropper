package com.bj.image_cropper.feature.image_editor.controller.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bj.image_cropper.compose_navigation.route.Routes
import com.bj.image_cropper.core.mvi.MVI
import com.bj.image_cropper.core.mvi.mvi
import com.bj.image_cropper.feature.image_editor.controller.contract.ImageEditorContract.SideEffect
import com.bj.image_cropper.feature.image_editor.controller.contract.ImageEditorContract.UiAction
import com.bj.image_cropper.feature.image_editor.controller.contract.ImageEditorContract.UiState

class ImageEditorViewModel(savedStateHandle: SavedStateHandle) : ViewModel(),
    MVI<UiState, UiAction, SideEffect> by mvi(UiState()) {
    private val passedUri = Routes.ImageEditor.from(savedStateHandle).uriObject

    init {
        updateState { copy(imageUri = passedUri) }
    }
}