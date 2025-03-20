package com.bj.image_cropper.feature.image_editor.presentation.ui.component

import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toIntRect
import androidx.compose.ui.unit.toSize
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.images.ImageSrc
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.utils.constrainOffset
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.utils.constrainResize
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.utils.eq
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.utils.keepAspect
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.utils.scaleToFit
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.utils.setSize
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.utils.toRect

@Stable
interface CropState {
    val src: ImageSrc
    var transform: ImgTransform
    var region: Rect
    var aspectLock: Boolean
    var shape: CropShape
    val accepted: Boolean
    fun done(accept: Boolean)
    fun reset()
}

internal fun CropState(
    src: ImageSrc,
    onDone: () -> Unit = {},
): CropState = object : CropState {
    val defaultTransform: ImgTransform = ImgTransform.Identity
    val defaultShape: CropShape = RectCropShape
    val defaultAspectLock: Boolean = false
    override val src: ImageSrc get() = src
    private var _transform: ImgTransform by mutableStateOf(defaultTransform)
    override var transform: ImgTransform
        get() = _transform
        set(value) {
            onTransformUpdated(transform, value)
            _transform = value
        }

    val defaultRegion = src.size.toSize().toRect()

    private var _region by mutableStateOf(defaultRegion)
    override var region
        get() = _region
        set(value) {
//            _region = value
            _region = updateRegion(
                old = _region, new = value,
                bounds = imgRect, aspectLock = aspectLock
            )
        }

    val imgRect by derivedStateOf { getTransformedImageRect(transform, src.size) }

    override var shape: CropShape by mutableStateOf(defaultShape)
    override var aspectLock by mutableStateOf(defaultAspectLock)

    private fun onTransformUpdated(old: ImgTransform, new: ImgTransform) {
        val unTransform = old.asMatrix(src.size).apply { invert() }
        _region = new.asMatrix(src.size).map(unTransform.map(region))
    }

    override fun reset() {
        transform = defaultTransform
        shape = defaultShape
        _region = defaultRegion
        aspectLock = defaultAspectLock
    }

    override var accepted: Boolean by mutableStateOf(false)

    override fun done(accept: Boolean) {
        accepted = accept
        onDone()
    }
}

internal fun getTransformedImageRect(transform: ImgTransform, size: IntSize): Rect {
    val dstMat = transform.asMatrix(size)
    return dstMat.map(size.toIntRect().toRect())
}

internal fun updateRegion(old: Rect, new: Rect, bounds: Rect, aspectLock: Boolean): Rect {
    val offsetOnly = old.width.eq(new.width) && old.height.eq(new.height)
    return if (offsetOnly) new.constrainOffset(bounds)
    else {
        val result = when {
            aspectLock -> new.keepAspect(old).scaleToFit(bounds, old)
            else -> new.constrainResize(bounds)
        }
        return when {
            result.isEmpty -> result.setSize(old, Size(1f, 1f)).constrainOffset(bounds)
            else -> result
        }
    }
}