package com.joshdev.smartpocket.ui.modules.arching.navigator

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingViewModel
import com.joshdev.smartpocket.ui.modules.arching.components.ArchingRecordsScreen
import com.joshdev.smartpocket.ui.modules.arching.components.ArchingScreen
import com.joshdev.smartpocket.ui.utils.appComposable

@Composable
fun Navigator(navController: NavHostController, viewModel: ArchingViewModel) {
    Surface(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        NavHost(navController = navController, startDestination = "arching") {
            appComposable("arching") {
                ArchingScreen(viewModel)
            }

            appComposable(
                route = "records/{archingId}",
                arguments = listOf(navArgument("archingId") { type = NavType.StringType })
            ) { backStackEntry ->
                val archingId = backStackEntry.arguments?.getString("archingId") ?: ""
                ArchingRecordsScreen(archingId, viewModel)
            }
        }
    }
}
