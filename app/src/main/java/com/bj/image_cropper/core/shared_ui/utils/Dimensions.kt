package com.bj.image_cropper.core.shared_ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp

/**
 * font scale aware extension
 */
val Number.Sp
    @Composable
    get() = (this.toInt() / LocalDensity.current.fontScale).sp