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
import com.joshdev.smartpocket.ui.modules.arching.screens.ArchingScreen
import com.joshdev.smartpocket.ui.modules.arching.screens.ProductScreen
import com.joshdev.smartpocket.ui.modules.arching.screens.RecordItemsScreen
import com.joshdev.smartpocket.ui.modules.arching.screens.RecordsScreen
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
                RecordsScreen(archingId, viewModel)
            }

            appComposable(
                route = "arcRecordItems/{recordId}",
                arguments = listOf(navArgument("recordId") { type = NavType.StringType })
            ) { backStackEntry ->
                val recordId = backStackEntry.arguments?.getLong("recordId") ?: 0L
                RecordItemsScreen(recordId, viewModel)
            }


            appComposable("ledProducts") {
                ProductScreen(viewModel)
            }
        }
    }
}
