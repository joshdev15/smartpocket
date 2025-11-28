package com.joshdev.smartpocket.ui.modules.ledger.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.joshdev.smartpocket.ui.modules.ledger.navigator.Navigator
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme

class LedgerActivity : ComponentActivity() {
    private val viewModel by viewModels<LedgerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            viewModel.start(this, this@LedgerActivity, navController)

            SmartPocketTheme {
                Navigator(navController, viewModel)
            }
        }
    }
}