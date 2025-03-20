package com.bj.image_cropper.feature.image_editor.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.bj.image_cropper.compose_navigation.navigator.AppNavigator
import com.bj.image_cropper.compose_navigation.navigator.NavigatorDelegation
import com.bj.image_cropper.compose_navigation.route.Routes
import com.bj.image_cropper.core.mvi.CollectSideEffect
import com.bj.image_cropper.feature.image_editor.presentation.controller.contract.ImageEditorContract
import com.bj.image_cropper.feature.image_editor.presentation.controller.vm.ImageEditorViewModel
import com.bj.image_cropper.feature.image_editor.presentation.ui.screen.ImageEditorScreen
import com.bj.image_cropper.feature.image_editor.utils.ImageEditorUtils.saveImageBitmapToCache
import org.koin.androidx.compose.koinViewModel

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
                        val imagePath = saveImageBitmapToCache(it.bitmapImage, context = context)
                        navigateAndClearBackStack(
                            Routes.Home(
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