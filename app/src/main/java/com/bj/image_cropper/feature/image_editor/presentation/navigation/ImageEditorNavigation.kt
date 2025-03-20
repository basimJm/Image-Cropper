package com.bj.image_cropper.feature.image_editor.presentation.navigation

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.bj.image_cropper.R
import com.bj.image_cropper.compose_navigation.navigator.AppNavigator
import com.bj.image_cropper.compose_navigation.navigator.NavigatorDelegation
import com.bj.image_cropper.compose_navigation.route.Routes
import com.bj.image_cropper.core.mvi.CollectSideEffect
import com.bj.image_cropper.feature.image_editor.presentation.controller.contract.ImageEditorContract
import com.bj.image_cropper.feature.image_editor.presentation.controller.vm.ImageEditorViewModel
import com.bj.image_cropper.feature.image_editor.presentation.ui.screen.ImageEditorScreen
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.io.FileOutputStream

class ImageEditorNavigation(override val navController: NavHostController) :
    AppNavigator by NavigatorDelegation(navController) {

    override fun registerGraph(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable<Routes.ImageEditor> {
            val viewModel = koinViewModel<ImageEditorViewModel>()
            val state = viewModel.uiState.collectAsStateWithLifecycle()
            val context = LocalContext.current
            CollectSideEffect(viewModel.sideEffect) {
                when (it) {
                    is ImageEditorContract.SideEffect.BackToHomeScreen -> {
                        fun saveImageBitmapToCache(
                            imageBitmap: ImageBitmap?,
                            context: Context
                        ): String? {
                            imageBitmap?.let {
                                val bitmap = imageBitmap.asAndroidBitmap()
                                val file =
                                    File(context.cacheDir, context.getString(R.string.image_type))
                                FileOutputStream(file).use {
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
                                }
                                return file.absolutePath
                            } ?: return null
                        }

                        val imagePath = saveImageBitmapToCache(it.bitmapImage, context = context)
                        navigateAndClearBackStack(
                            Routes.Home(
                                imageUri = it.imageUri.toString(),
                                imagePath = imagePath
                            )
                        )
                    }
                }
            }
            ImageEditorScreen(state.value, viewModel::onEvent)
        }
    }
}