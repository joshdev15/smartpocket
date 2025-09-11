package com.joshdev.smartpocket.ui.activities.photoai.subcomponents

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.joshdev.smartpocket.ui.activities.photoai.PhotoAIViewModel

@Composable
fun PhotoNavigator(viewModel: PhotoAIViewModel, navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(navController, startDestination = "camera") {
        composable("camera") { PhotoCamera(innerPadding, viewModel) }
        composable("analysis") { PhotoAnalysis(innerPadding, viewModel) }
        composable("edition") { PhotoEditor(innerPadding, viewModel) }
    }
}