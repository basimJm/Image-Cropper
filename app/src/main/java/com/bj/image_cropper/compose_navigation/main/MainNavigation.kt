package com.bj.image_cropper.compose_navigation.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bj.image_cropper.compose_navigation.route.Routes
import com.bj.image_cropper.feature.home.presentation.navigation.HomeNavigation
import com.bj.image_cropper.feature.cropper.presentation.navigation.ImageCropperNavigation

@Composable
fun MainNavigation(
    navController: NavHostController,
    startDestination: Routes,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        HomeNavigation(navController).registerGraph(this)
        ImageCropperNavigation(navController).registerGraph(this)
    }
}