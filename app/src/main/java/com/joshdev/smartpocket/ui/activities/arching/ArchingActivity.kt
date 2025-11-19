package com.joshdev.smartpocket.ui.activities.arching

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import com.joshdev.smartpocket.ui.activities.arching.subcomponents.ArchingScreen
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme

class ArchingActivity : ComponentActivity() {
    private val viewModel by viewModels<ArchingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.start(this, this@ArchingActivity)

        setContent {
            SmartPocketTheme {
                Scaffold(
                    topBar = {
                        AppTopBarBasic("Cierres")
                    },
                    floatingActionButton = {
                        FloatingButton("Nuevo Cierre") {
                            viewModel.toggleNewArchingDialog(
                                true
                            )
                        }
                    },
                    content = { innerPadding ->
                        ArchingScreen(innerPadding, viewModel)
                    }
                )
            }
        }
    }
}