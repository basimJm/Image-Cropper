package com.bj.image_cropper.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.bj.image_cropper.compose_navigation.main.MainNavigation
import com.bj.image_cropper.compose_navigation.route.Routes
import com.bj.image_cropper.core.shared_ui.theme.ImageCropperTheme

@Composable
fun MainScreen(navController: NavHostController) {
    ImageCropperTheme {
        MainNavigation(navController = navController, startDestination = Routes.Home)
    }
}