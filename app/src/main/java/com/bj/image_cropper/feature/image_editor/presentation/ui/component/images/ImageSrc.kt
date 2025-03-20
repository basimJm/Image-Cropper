package com.bj.image_cropper.feature.image_editor.presentation.ui.component.images

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toIntRect

@Stable
interface ImageSrc {
    val size: IntSize
    suspend fun open(params: DecodeParams): DecodeResult?
}

internal data class ImageBitmapSrc(private val data: ImageBitmap) : ImageSrc {
    override val size: IntSize = IntSize(data.width, data.height)
    private val resultParams = DecodeParams(1, size.toIntRect())
    override suspend fun open(params: DecodeParams) = DecodeResult(resultParams, data)
}