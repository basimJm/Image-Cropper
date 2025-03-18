package com.bj.image_cropper.feature.home.presentation.controller.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bj.image_cropper.R
import com.bj.image_cropper.core.mvi.MVI
import com.bj.image_cropper.core.mvi.mvi
import com.bj.image_cropper.feature.home.presentation.controller.contract.HomeContract.SideEffect
import com.bj.image_cropper.feature.home.presentation.controller.contract.HomeContract.UiAction
import com.bj.image_cropper.feature.home.presentation.controller.contract.HomeContract.UiState
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(),
    MVI<UiState, UiAction, SideEffect> by mvi(UiState()) {
    override fun onEvent(event: UiAction) {
        viewModelScope.launch {
            when (event) {
                is UiAction.OnImageSelected -> {
                    updateState {
                        copy(
                            selectedImageUri = event.uri,
                            buttonTextResource = R.string.edit_image
                        )
                    }
                    emitSideEffect(SideEffect.ShowMessageFromRes(R.string.image_selected_successfully))
                }

                UiAction.OnNavigateToImageEditorScreen -> {
                    emitSideEffect(
                        SideEffect.NavigateToImageEditorScreen(
                            uiState.value.selectedImageUri!!
                        )
                    )
                }
            }
        }
    }
}