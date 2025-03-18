package com.bj.image_cropper.feature.image_editor.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.bj.image_cropper.core.shared_ui.composable.AppScreen
import com.bj.image_cropper.feature.image_editor.controller.contract.ImageEditorContract
import com.bj.image_cropper.feature.image_editor.ui.component.CropState
import com.bj.image_cropper.feature.image_editor.ui.component.CropperLoading
import com.bj.image_cropper.feature.image_editor.ui.component.ImageCropperDialog
import com.bj.image_cropper.feature.image_editor.ui.component.LoadingDialog

@Composable
fun ImageEditorScreen(
    state: ImageEditorContract.UiState,
    pushEvent: (ImageEditorContract.UiAction) -> Unit
) {
    AppScreen() {
        DemoContent(
            cropState = state.imageCropper.cropState,
            loadingStatus = state.imageCropper.loadingStatus,
            selectedImage = state.bitmapImage,
            onPick = { },
        )
    }
}


@Composable
fun DemoContent(
    cropState: CropState?,
    loadingStatus: CropperLoading?,
    selectedImage: ImageBitmap?,
    onPick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (cropState != null) {
        ImageCropperDialog(state = cropState)

    }
    if (cropState == null && loadingStatus != null) {
        LoadingDialog(status = loadingStatus)
    }
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (selectedImage != null) Image(
            bitmap = selectedImage, contentDescription = null,
            modifier = Modifier.weight(1f)
        ) else Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(1f)) {
            Text("No image selected !")
        }
        Button(onClick = onPick) { Text("Choose Image") }
    }
}