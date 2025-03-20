package com.bj.image_cropper.feature.image_editor.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bj.image_cropper.R
import com.bj.image_cropper.core.domain.entity.AppBarAction
import com.bj.image_cropper.core.shared_ui.composable.AppScreen
import com.bj.image_cropper.feature.image_editor.presentation.controller.contract.ImageEditorContract
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.CropperControls
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.CropperPreview

@Composable
fun ImageEditorScreen(
    state: ImageEditorContract.UiState,
    pushEvent: (ImageEditorContract.UiAction) -> Unit
) {
    AppScreen(
        title = stringResource(R.string.edit_image),
        onBackPressed = { pushEvent(ImageEditorContract.UiAction.OnBackPressed) }, actions = listOf(
            AppBarAction(
                icon = Icons.Default.Done,
                contentDescription = stringResource(R.string.done_icon),
                onClicked = {
                    state.imageCropper.cropState?.done(accept = true)
                },
            )
        )
    ) {
        if (state.imageCropper.cropState != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                state.imageCropper.cropState?.let {
                    CropperPreview(
                        state = it,
                        modifier = Modifier.weight(1f)
                    )
                    CropperControls(
                        state = it,
                        modifier = Modifier
                            .padding(12.dp),
                    )
                }
            }
        }
    }
}
