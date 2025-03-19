package com.bj.image_cropper.feature.image_editor.navigation

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.bj.image_cropper.compose_navigation.navigator.AppNavigator
import com.bj.image_cropper.compose_navigation.navigator.NavigatorDelegation
import com.bj.image_cropper.compose_navigation.route.Routes
import com.bj.image_cropper.core.mvi.CollectSideEffect
import com.bj.image_cropper.core.utils.Extensions.toBase64
import com.bj.image_cropper.feature.image_editor.controller.contract.ImageEditorContract
import com.bj.image_cropper.feature.image_editor.controller.vm.ImageEditorViewModel
import com.bj.image_cropper.feature.image_editor.ui.screen.ImageEditorScreen
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.io.FileOutputStream

class ImageEditorNavigation(override val navController: NavHostController) :
    AppNavigator by NavigatorDelegation(navController) {

    fun saveImageBitmapToCache(imageBitmap: ImageBitmap?, context: Context): String? {
        imageBitmap?.let { val bitmap = imageBitmap.asAndroidBitmap()
            val file = File(context.cacheDir, "image.png")  // Cache directory
            FileOutputStream(file).use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
            return file.absolutePath }?: return null
    }

    override fun registerGraph(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable<Routes.ImageEditor> {
            val viewModel = koinViewModel<ImageEditorViewModel>()
            val state = viewModel.uiState.collectAsStateWithLifecycle()
            val context = LocalContext.current
            CollectSideEffect(viewModel.sideEffect) {
                when (it) {
                    is ImageEditorContract.SideEffect.BackToHomeScreen -> {
                        val imagePath = saveImageBitmapToCache(it.bitmapImage!! , context)
                        navigateTo(
                            Routes.Home(
                                imageUri = it.imageUri.toString(), imagePath = imagePath)
                            )
                    }
                }
            }
            ImageEditorScreen(state.value, viewModel::onEvent)
        }
    }
}