package com.joshdev.smartpocket.ui.activities.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import com.joshdev.smartpocket.ui.activities.home.subcomponents.HomeScreen
import com.joshdev.smartpocket.ui.components.AppBasicTopBar
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme

class HomeActivity : ComponentActivity() {
    private val viewModel by viewModels<HomeViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.start(this, this@HomeActivity)

        setContent {
            SmartPocketTheme {
                Scaffold(
                    topBar = { AppBasicTopBar() },
                    floatingActionButton = {
                        FloatingButton("Nuevo Registro") { viewModel.toggleNewRecordDialog(true) }
                    },
                    content = { innerPadding -> HomeScreen(innerPadding, viewModel) }
                )
            }
        }
    }
}