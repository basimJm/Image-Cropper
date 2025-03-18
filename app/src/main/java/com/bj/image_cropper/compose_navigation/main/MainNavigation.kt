package com.bj.image_cropper.compose_navigation.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bj.image_cropper.compose_navigation.route.Routes
import com.bj.image_cropper.feature.home.presentation.navigation.HomeNavigation
import com.bj.image_cropper.feature.image_editor.navigation.ImageEditorNavigation

@Composable
fun MainNavigation(
    navController: NavHostController,
    startDestination: Routes,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        HomeNavigation(navController).registerGraph(this)
        ImageEditorNavigation(navController).registerGraph(this)
    }
}