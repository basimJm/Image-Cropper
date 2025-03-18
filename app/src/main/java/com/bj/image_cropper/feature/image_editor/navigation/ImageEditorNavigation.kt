package com.bj.image_cropper.feature.image_editor.navigation

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.bj.image_cropper.compose_navigation.navigator.AppNavigator
import com.bj.image_cropper.compose_navigation.navigator.NavigatorDelegation
import com.bj.image_cropper.compose_navigation.route.Routes
import com.bj.image_cropper.feature.image_editor.controller.vm.ImageEditorViewModel
import com.bj.image_cropper.feature.image_editor.ui.screen.ImageEditorScreen
import org.koin.androidx.compose.koinViewModel

class ImageEditorNavigation(override val navController: NavHostController) :
    AppNavigator by NavigatorDelegation(navController) {

    override fun registerGraph(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable<Routes.ImageEditor> {
            val viewModel = koinViewModel<ImageEditorViewModel>()
            val state = viewModel.uiState.collectAsStateWithLifecycle()
            ImageEditorScreen(state.value, viewModel::onEvent)
        }
    }
}