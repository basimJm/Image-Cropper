package com.bj.image_cropper.core.domain.entity

data class AppBarAction(
    val iconId: Int,
    val contentDescription: String,
    val onClicked: () -> Unit,
)