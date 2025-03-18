package com.bj.image_cropper.feature.home.presentation.ui.component

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.bj.image_cropper.feature.home.presentation.controller.contract.HomeContract.UiAction

@Composable
fun managedActivityResultLauncher(pushEvent: (UiAction) -> Unit): ManagedActivityResultLauncher<String, Uri?> {
    val getImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri == null) return@rememberLauncherForActivityResult
            pushEvent(UiAction.OnImageSelected(uri))
        }
    )
    return getImage
}