package com.bj.image_cropper.core.domain.entity

import androidx.compose.ui.graphics.vector.ImageVector

data class AppBarAction(
    val icon: ImageVector,
    val contentDescription: String,
    val onClicked: () -> Unit,
)