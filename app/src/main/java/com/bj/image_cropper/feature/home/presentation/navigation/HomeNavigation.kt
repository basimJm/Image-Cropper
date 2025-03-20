package com.bj.image_cropper.feature.home.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.bj.image_cropper.compose_navigation.navigator.AppNavigator
import com.bj.image_cropper.compose_navigation.navigator.NavigatorDelegation
import com.bj.image_cropper.compose_navigation.route.Routes
import com.bj.image_cropper.core.mvi.CollectSideEffect
import com.bj.image_cropper.core.shared_ui.utils.showMessage
import com.bj.image_cropper.feature.home.presentation.controller.contract.HomeContract
import com.bj.image_cropper.feature.home.presentation.controller.vm.HomeViewModel
import com.bj.image_cropper.feature.home.presentation.ui.screen.HomeScreen
import org.koin.androidx.compose.koinViewModel

class HomeNavigation(override val navController: NavHostController) :
    AppNavigator by NavigatorDelegation(navController) {

    override fun registerGraph(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable<Routes.Home> {
            val viewModel = koinViewModel<HomeViewModel>()
            val state = viewModel.uiState.collectAsStateWithLifecycle()
            val context = LocalContext.current
            CollectSideEffect(viewModel.sideEffect) { sideEffect ->
                when (sideEffect) {
                    is HomeContract.SideEffect.ShowMessageFromRes -> {
                        context.showMessage(
                            sideEffect.resId
                        )
                    }

                    is HomeContract.SideEffect.NavigateToImageEditorScreen -> {
                        navigateTo(Routes.ImageEditor(sideEffect.uri.toString()))
                    }
                }
            }
            HomeScreen(state.value, viewModel::onEvent)
        }
    }
}