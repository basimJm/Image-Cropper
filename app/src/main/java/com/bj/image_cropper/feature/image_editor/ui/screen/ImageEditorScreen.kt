package com.bj.image_cropper.feature.image_editor.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.bj.image_cropper.R
import com.bj.image_cropper.core.shared_ui.composable.AppScreen
import com.bj.image_cropper.core.shared_ui.composable.AppText
import com.bj.image_cropper.core.shared_ui.utils.Sp
import com.bj.image_cropper.feature.image_editor.controller.contract.ImageEditorContract

@Composable
fun ImageEditorScreen(state: ImageEditorContract.UiState , pushEvent: (ImageEditorContract.UiAction) -> Unit) {
    AppScreen() {
        state.imageUri?.let {
            val painter: Painter = rememberAsyncImagePainter(it)
            Image(
                painter = painter,
                contentDescription = stringResource(R.string.selected_image),
                modifier = Modifier.size(200.dp).align(Alignment.Center)
            )
        } ?: run {
            AppText(
                text = stringResource(R.string.no_image_selected),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 40.Sp
                )
            )
        }
    }
}