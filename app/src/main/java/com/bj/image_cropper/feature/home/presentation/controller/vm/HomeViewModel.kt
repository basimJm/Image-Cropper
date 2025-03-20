package com.bj.image_cropper.feature.home.presentation.controller.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bj.image_cropper.R
import com.bj.image_cropper.compose_navigation.route.Routes
import com.bj.image_cropper.core.mvi.MVI
import com.bj.image_cropper.core.mvi.mvi
import com.bj.image_cropper.feature.home.presentation.controller.contract.HomeContract.SideEffect
import com.bj.image_cropper.feature.home.presentation.controller.contract.HomeContract.UiAction
import com.bj.image_cropper.feature.home.presentation.controller.contract.HomeContract.UiState
import kotlinx.coroutines.launch

class HomeViewModel(private val saveStateHandle: SavedStateHandle) : ViewModel(),
    MVI<UiState, UiAction, SideEffect> by mvi(UiState()) {

    init {
        initializeState()
    }

    private fun initializeState() {
        val passedData = Routes.Home.from(saveStateHandle)
        passedData.imagePath?.let {
            updateState { copy(imagePath = it) }
        }
    }

    override fun onEvent(event: UiAction) {
        viewModelScope.launch {
            when (event) {
                is UiAction.OnImageSelected -> {
                    updateState {
                        copy(
                            imageUri = event.uri,
                            buttonTextResource = R.string.edit_image
                        )
                    }
                    emitSideEffect(SideEffect.ShowMessageFromRes(R.string.image_selected_successfully))
                }

                UiAction.OnNavigateToImageEditorScreen -> {
                    emitSideEffect(
                        SideEffect.NavigateToImageEditorScreen(
                            uiState.value.imageUri!!
                        )
                    )
                }
            }
        }
    }
}