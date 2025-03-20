package com.bj.image_cropper.feature.image_editor.presentation.ui.component

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.IntSize
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.images.ImageSrc
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.images.toImageSrc
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

sealed interface CropResult {
    data class Success(val bitmap: ImageBitmap) : CropResult

    object Cancelled : CropResult
}

enum class CropError : CropResult {
    LoadingError,
    SavingError
}

enum class CropperLoading {
    PreparingImage,
    SavingResult,
}

internal val DefaultMaxCropSize = IntSize(3000, 3000)

@Stable
interface ImageCropper {
    val cropState: CropState?

    val loadingStatus: CropperLoading?

    suspend fun crop(
        maxResultSize: IntSize? = DefaultMaxCropSize,
        createSrc: suspend () -> ImageSrc?
    ): CropResult
}

suspend fun ImageCropper.crop(
    uri: Uri,
    context: Context,
    maxResultSize: IntSize? = DefaultMaxCropSize,
    cacheBeforeUse: Boolean = true
): CropResult = cacheUri(enabled = cacheBeforeUse, uri, context) { cached ->
    crop(maxResultSize) { cached?.toImageSrc(context) }
}

private const val CacheDir = "image_cropper_cache"

private suspend fun <R> cacheUri(
    enabled: Boolean, uri: Uri, context: Context,
    block: suspend (Uri?) -> R
): R {
    if (!enabled) return block(uri)
    val dst = context.cacheDir.resolve("$CacheDir/${UUID.randomUUID()}")
    return try {
        val cached = runCatching { copy(uri, dst, context) }.getOrNull()
        block(cached)
    } finally {
        dst.deleteInBackground()
    }
}

@OptIn(DelicateCoroutinesApi::class)
private fun File.deleteInBackground() {
    GlobalScope.launch(Dispatchers.IO) { runCatching { delete() } }
}

private suspend fun copy(src: Uri, dst: File, context: Context) = withContext(Dispatchers.IO) {
    dst.parentFile?.mkdirs()
    context.contentResolver.openInputStream(src)!!.use { srcStream ->
        dst.outputStream().use { dstStream ->
            srcStream.copyTo(dstStream)
        }
    }
    Uri.fromFile(dst)
}

fun ImageCropper(): ImageCropper = object : ImageCropper {
    override var cropState: CropState? by mutableStateOf(null)
    private val cropStateFlow = snapshotFlow { cropState }
    override var loadingStatus: CropperLoading? by mutableStateOf(null)
    override suspend fun crop(
        maxResultSize: IntSize?,
        createSrc: suspend () -> ImageSrc?
    ): CropResult {
        cropState = null
        val src = withLoading(CropperLoading.PreparingImage) { createSrc() }
            ?: return CropError.LoadingError
        val newCrop = CropState(src) { cropState = null }
        cropState = newCrop
        cropStateFlow.takeWhile { it === newCrop }.collect()
        if (!newCrop.accepted) return CropResult.Cancelled
        return withLoading(CropperLoading.SavingResult) {
            val result = newCrop.createResult(maxResultSize)
            if (result == null) CropError.SavingError
            else CropResult.Success(result)
        }
    }

    inline fun <R> withLoading(status: CropperLoading, op: () -> R): R {
        return try {
            loadingStatus = status
            op()
        } finally {
            loadingStatus = null
        }
    }
}