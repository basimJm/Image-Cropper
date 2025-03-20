package com.bj.image_cropper.feature.image_editor.presentation.ui.component

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.unit.IntSize
import com.bj.image_cropper.feature.image_editor.presentation.ui.component.images.ImageBitmapSrc
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

/** Union type denoting the possible results after a crop operation is done */
public sealed interface CropResult {
    /** The final result as an ImageBitmap.
     * use [asAndroidBitmap] if you need an [android.graphics.Bitmap].
     */
    data class Success(val bitmap: ImageBitmap) : CropResult

    /** The user has cancelled the operation or another session was started. */
    object Cancelled : CropResult
}

public enum class CropError : CropResult {
    /** The supplied image is invalid, not supported by the codec
     * or you don't have the required permissions to read it */
    LoadingError,

    /** The result could not be saved. Try reducing the maxSize supplied to [ImageCropper.crop] */
    SavingError
}

public enum class CropperLoading {
    /** The image is being prepared. */
    PreparingImage,

    /** The user has accepted the cropped image and the result is being saved. */
    SavingResult,
}

internal val DefaultMaxCropSize = IntSize(3000, 3000)

/**
 * State holder for the image cropper.
 * Allows starting new crop sessions as well as getting the state of the pending crop.
 */
@Stable
public interface ImageCropper {
    /** The pending crop state, if any */
    val cropState: CropState?

    val loadingStatus: CropperLoading?

    /**
     * Initiates a new crop session, cancelling the current one, if any.
     * Suspends until a result is available (cancellation, error, success) and returns it.
     * The resulting image will be scaled down to fit [maxResultSize] (if provided).
     * [createSrc] will be used to construct an [ImageSrc] instance.
     */
    suspend fun crop(
        maxResultSize: IntSize? = DefaultMaxCropSize,
        createSrc: suspend () -> ImageSrc?
    ): CropResult
}

/**
 * Initiates a new crop session, cancelling the current one, if any.
 * Suspends until a result is available (cancellation, error, success) and returns it.
 * The resulting image will be scaled down to fit [maxResultSize] if provided.
 * [bmp] will be used as a source.
 */
public suspend fun ImageCropper.crop(
    maxResultSize: IntSize? = DefaultMaxCropSize,
    bmp: ImageBitmap
): CropResult = crop(maxResultSize = maxResultSize) {
    ImageBitmapSrc(bmp)
}

public suspend fun ImageCropper.crop(
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

@Composable
public fun rememberImageCropper(): ImageCropper {
    return remember { ImageCropper() }
}

/**
 * Creates an [ImageCropper] instance.
 */
public fun ImageCropper(): ImageCropper = object : ImageCropper {
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