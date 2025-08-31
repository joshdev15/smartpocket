package com.joshdev.smartpocket.ui.activities.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.ui.activities.home.subcomponents.HomeScreen
import com.joshdev.smartpocket.ui.components.AppBasicTopBar
import com.joshdev.smartpocket.ui.components.AppMenuTopBar
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {
    private val viewModel by viewModels<HomeViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.start(this, this@HomeActivity)

        setContent {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            SmartPocketTheme {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(200.dp)
                                .background(MaterialTheme.colorScheme.surfaceBright)
                                .padding(top = 200.dp)
                                .padding(horizontal = 10.dp)
                        ) {
                            AppText("Opcion 1", color = MaterialTheme.colorScheme.onBackground)
                            AppText("Opcion 2", color = MaterialTheme.colorScheme.onBackground)
                        }
                    },
                    content = {
                        Scaffold(
                            topBar = {
                                AppMenuTopBar("Registros") {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            },
                            floatingActionButton = {
                                FloatingButton("Nuevo Registro") {
                                    viewModel.toggleNewRecordDialog(
                                        true
                                    )
                                }
                            },
                            content = { innerPadding -> HomeScreen(innerPadding, viewModel) }
                        )
                    }
                )
            }
        }
    }
}