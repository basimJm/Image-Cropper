package com.bj.image_cropper.feature.image_editor.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun LoadingDialog(status: CropperLoading) {
    var dismissed by remember(status) { mutableStateOf(false) }
    if (!dismissed) Dialog(onDismissRequest = { dismissed = true }) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            CircularProgressIndicator()
            Text(text = status.getMessage())
        }
    }
}

@Composable
fun CropperLoading.getMessage(): String {
    return remember(this) {
        when (this) {
            CropperLoading.PreparingImage -> "Preparing Image"
            CropperLoading.SavingResult -> "Saving Result"
        }
    }
}