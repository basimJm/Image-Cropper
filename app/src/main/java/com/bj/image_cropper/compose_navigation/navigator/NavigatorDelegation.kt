package com.bj.image_cropper.compose_navigation.navigator

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.bj.image_cropper.compose_navigation.route.Routes

class NavigatorDelegation(override val navController: NavHostController) :AppNavigator {
    override fun registerGraph(navGraphBuilder: NavGraphBuilder) {

    }

    override fun navigateTo(route: Routes) {
        navController.navigate(route)
    }

    override fun navigateBack() {
        navController.popBackStack()
    }
}