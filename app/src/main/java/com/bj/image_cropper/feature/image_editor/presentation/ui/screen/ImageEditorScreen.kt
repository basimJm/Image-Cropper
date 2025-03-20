package com.bj.image_cropper.feature.image_editor.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bj.image_cropper.R
import com.bj.image_cropper.core.shared_ui.composable.AppScreen
import com.bj.image_cropper.feature.image_editor.presentation.controller.contract.ImageEditorContract
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.CropperPreview
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.DefaultControls

@Composable
fun ImageEditorScreen(
    state: ImageEditorContract.UiState,
    pushEvent: (ImageEditorContract.UiAction) -> Unit
) {
    AppScreen(withAppBar = false) {
        if (state.imageCropper.cropState != null) {
            Column(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxSize()
            ) {
                Row {
                    IconButton(onClick = { state.imageCropper.cropState?.reset() }) {
                        Icon(painterResource(R.drawable.restore), null)
                    }
                    IconButton(
                        onClick = {
                            state.imageCropper.cropState?.done(accept = true)
                        },
                        enabled = state.imageCropper.cropState?.accepted == false
                    ) {
                        Icon(Icons.Default.Done, null)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clipToBounds()
                        .background(color = androidx.compose.ui.graphics.Color.Black)
                ) {
                    CropperPreview(
                        state = state.imageCropper.cropState!!,
                        modifier = Modifier.fillMaxSize()
                    )
                    DefaultControls(state = state.imageCropper.cropState!!)
                }
            }
        }
    }
}
