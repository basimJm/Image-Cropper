package com.bj.image_cropper.core.shared_ui.utils

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.bj.image_cropper.R

object FontProvider {
    val cairoRegular = FontFamily(Font(R.font.cairo_regular, FontWeight.Normal))
    val cairoMedium = FontFamily(Font(R.font.cairo_medium, FontWeight.Medium))
    val cairoSemiBold = FontFamily(Font(R.font.cairo_semi_bold, FontWeight.SemiBold))
    val cairoBold = FontFamily(Font(R.font.cairo_bold, FontWeight.Bold))
}