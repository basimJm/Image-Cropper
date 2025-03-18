package com.bj.image_cropper.compose_navigation.navigator

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.bj.image_cropper.compose_navigation.route.Routes

interface AppNavigator {
    val navController: NavHostController
    fun registerGraph(navGraphBuilder: NavGraphBuilder)
    fun navigateTo(route: Routes)
    fun navigateBack()
}
