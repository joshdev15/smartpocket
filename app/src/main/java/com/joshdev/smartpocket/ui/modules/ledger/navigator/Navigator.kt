package com.joshdev.smartpocket.ui.modules.ledger.navigator

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.joshdev.smartpocket.ui.modules.ledger.activity.LedgerViewModel
import com.joshdev.smartpocket.ui.modules.ledger.screens.CategoriesScreen
import com.joshdev.smartpocket.ui.modules.ledger.screens.LedgerScreen
import com.joshdev.smartpocket.ui.modules.ledger.screens.TransactionScreen
import com.joshdev.smartpocket.ui.utils.appComposable

@Composable
fun Navigator(navController: NavHostController, viewModel: LedgerViewModel) {
    Surface(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        NavHost(navController = navController, startDestination = "ledger") {
            appComposable("ledger") {
                LedgerScreen(viewModel)
            }

            appComposable(
                route = "transactions/{ledgerId}",
                arguments = listOf(navArgument("ledgerId") { type = NavType.StringType })
            ) { backStackEntry ->
                val ledgerId = backStackEntry.arguments?.getLong("ledgerId") ?: 0L
                TransactionScreen(viewModel, ledgerId)
            }

            appComposable("categories") {
                CategoriesScreen(viewModel)
            }
        }
    }
}