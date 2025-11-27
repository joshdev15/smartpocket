package com.joshdev.smartpocket.ui.micromodules.arching.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.joshdev.smartpocket.ui.micromodules.arching.components.ArchingRecordsScreen
import com.joshdev.smartpocket.ui.micromodules.arching.components.ArchingScreen
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme
import com.joshdev.smartpocket.ui.utils.appComposable

class ArchingActivity : ComponentActivity() {
    private val viewModel by viewModels<ArchingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.start(this, this@ArchingActivity)

        setContent {
            SmartPocketTheme {
                val navController = rememberNavController()
                viewModel.setNavController(navController)

                Surface(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
                ) {
                    NavHost(navController = navController, startDestination = "archings") {
                        appComposable(
                            route = "archings",
                            content = {
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
                        )

                        appComposable(
                            route = "records/{archingId}",
                            content = { backStackEntry ->
                                val archingId = backStackEntry.arguments?.getString("archingId")
                                val currentArching =
                                    viewModel.archings.value.find { it.id == archingId }

                                currentArching?.let { current ->
                                    Scaffold(
                                        topBar = {
                                            AppTopBarBasic(current.name)
                                        },
                                        floatingActionButton = {
                                            FloatingButton("Nuevo Registro") {
                                                viewModel.toggleNewArchingRecordDialog(
                                                    true
                                                )
                                            }
                                        },
                                        content = {
                                            ArchingRecordsScreen(
                                                it,
                                                archingId = archingId,
                                                viewModel = viewModel
                                            )
                                        }
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}