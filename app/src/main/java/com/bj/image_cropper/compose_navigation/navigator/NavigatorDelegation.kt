package com.bj.image_cropper.compose_navigation.navigator

import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.bj.image_cropper.compose_navigation.route.Routes

class NavigatorDelegation(override val navController: NavHostController) : AppNavigator {
    override fun registerGraph(navGraphBuilder: NavGraphBuilder) {

    }

    override fun navigateTo(route: Routes) {
        navController.navigate(route)
    }

 override fun navigateAndClearBackStack(
        route: Routes,
    ) {
        val selected =
            navController.currentDestination?.hierarchy?.any { it.hasRoute(route::class) } == true
        if (selected) {
            return
        }

        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    override fun navigateBack() {
        navController.popBackStack()
    }
}