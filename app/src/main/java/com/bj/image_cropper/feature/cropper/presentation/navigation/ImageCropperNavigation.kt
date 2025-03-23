package com.bj.image_cropper.feature.cropper.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.bj.image_cropper.compose_navigation.navigator.AppNavigator
import com.bj.image_cropper.compose_navigation.navigator.NavigatorDelegation
import com.bj.image_cropper.compose_navigation.route.Routes
import com.bj.image_cropper.core.mvi.CollectSideEffect
import com.bj.image_cropper.feature.cropper.presentation.controller.contract.ImageCropperContract
import com.bj.image_cropper.feature.cropper.presentation.controller.vm.ImageCropperViewModel
import com.bj.image_cropper.feature.cropper.presentation.ui.screen.ImageCropperScreen
import com.bj.image_cropper.feature.cropper.utils.ImageCropperUtils.saveImageBitmapToCache
import org.koin.androidx.compose.koinViewModel

class ImageCropperNavigation(override val navController: NavHostController) :
    AppNavigator by NavigatorDelegation(navController) {

    override fun registerGraph(navGraphBuilder: NavGraphBuilder) {

        navGraphBuilder.composable<Routes.ImageCropper> {
            val viewModel = koinViewModel<ImageCropperViewModel>()
            val state = viewModel.uiState.collectAsStateWithLifecycle()
            val context = LocalContext.current

            CollectSideEffect(viewModel.sideEffect) {
                when (it) {
                    is ImageCropperContract.SideEffect.BackToHomeScreen -> {
                        val imagePath = saveImageBitmapToCache(it.bitmapImage, context = context)
                        navigateAndClearBackStack(
                            Routes.Home(
                                imagePath = imagePath
                            )
                        )
                    }
                }
            }
            ImageCropperScreen(state.value, viewModel::onEvent)
        }
    }
}