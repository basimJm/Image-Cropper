package com.bj.image_cropper.core.shared_ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class Dimensions {
    val default: Dp = 0.dp
    val spaceVerySmall: Dp = 4.dp
    val spaceSmall: Dp = 8.dp
    val spaceExtraSmall: Dp = 12.dp
    val spaceMedium: Dp = 16.dp
    val spaceExtraMedium: Dp = 24.dp
    val spaceLarge: Dp = 32.dp
    val spaceXLarge: Dp = 40.dp
    val spaceXXLarge: Dp = 64.dp

    val defaultCornerRadius: Dp = 3.dp
    val defaultCardElevation: Dp = 40.dp
    val textFieldCornerRadius: Dp = 5.dp
    val surfaceCornerRadius: Dp = 20.dp

    val buttonHeight: Dp = 65.dp
}

/**
 * font scale aware extension
 */
val Number.Sp
    @Composable
    get() = (this.toInt() / LocalDensity.current.fontScale).sp

/**
 * size scale aware extension
 */
val Number.Dp
    @Composable
    get() = (this.toInt() / LocalDensity.current.density).dp

val LocalSpacing = compositionLocalOf { Dimensions() }