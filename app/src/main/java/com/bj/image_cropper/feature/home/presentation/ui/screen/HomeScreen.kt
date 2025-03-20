package com.bj.image_cropper.feature.home.presentation.ui.screen

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.bj.image_cropper.R
import com.bj.image_cropper.core.shared_ui.composable.AppButton
import com.bj.image_cropper.core.shared_ui.composable.AppScreen
import com.bj.image_cropper.core.shared_ui.composable.AppText
import com.bj.image_cropper.core.shared_ui.theme.ImageCropperTheme
import com.bj.image_cropper.core.shared_ui.utils.Sp
import com.bj.image_cropper.feature.home.presentation.controller.contract.HomeContract.UiAction
import com.bj.image_cropper.feature.home.presentation.controller.contract.HomeContract.UiState
import com.bj.image_cropper.feature.home.presentation.ui.component.managedActivityResultLauncher

@Composable
fun HomeScreen(
    state: UiState, pushEvent: (UiAction) -> Unit
) {
    val getImage = managedActivityResultLauncher(pushEvent)
    val context = LocalContext.current

    AppScreen {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            state.imageUri?.let { imageUri ->
                val painter = rememberAsyncImagePainter(imageUri)
                Image(
                    painter = painter,
                    contentDescription = stringResource(R.string.selected_image),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                )
            } ?: state.imagePath?.let { image ->
                val bitmap = BitmapFactory.decodeFile(image)
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp), bitmap = bitmap.asImageBitmap(), contentDescription = null
                )
            } ?: run {
                AppText(
                    text = stringResource(R.string.no_image_selected),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 40.Sp
                    )
                )
            }

            AppButton(
                modifier = Modifier.width(150.dp),
                title = stringResource(state.buttonTextResource),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 12.Sp,
                    textAlign = TextAlign.Center
                )
            ) {
                state.imageUri?.let {
                    pushEvent(UiAction.OnNavigateToImageEditorScreen)
                } ?: run {
                    getImage.launch(context.getString(R.string.image_type))
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    ImageCropperTheme {
        HomeScreen(
            state = UiState(
                imageUri = null
            ), pushEvent = {})
    }
}

