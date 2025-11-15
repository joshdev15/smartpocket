package com.joshdev.smartpocket.ui.activities.ledger

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.joshdev.smartpocket.R
import com.joshdev.smartpocket.ui.activities.categoryList.CategoryListActivity
import com.joshdev.smartpocket.ui.activities.currency.CurrencyActivity
import com.joshdev.smartpocket.ui.activities.ledger.subcomponents.LedgerScreen
import com.joshdev.smartpocket.ui.components.AppTopBarMenu
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.components.MenuOptionContainer
import com.joshdev.smartpocket.ui.models.MenuOption
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme
import com.joshdev.smartpocket.ui.utils.UiUtils.showToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LedgerActivity : ComponentActivity() {
    private val viewModel by viewModels<LedgerViewModel>()
    private var cameraAllowed = false
    private var allowExit = false
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            cameraAllowed = isGranted
        }

    @RequiresApi(Build.VERSION_CODES.S)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        checkCameraPermissionAndLaunch()

        viewModel.start(this, this@LedgerActivity)

        setContent {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val options = listOf(
                MenuOption(R.drawable.category, "Categorías") {
                    scope.launch { drawerState.close() }
                    val goToCategoryList = Intent(this, CategoryListActivity::class.java)
                    startActivity(goToCategoryList)
                },
                MenuOption(R.drawable.dollar, "Divisas") {
                    scope.launch { drawerState.close() }
                    val goToCurrency = Intent(this, CurrencyActivity::class.java)
                    startActivity(goToCurrency)
                },
            )

            BackHandler {
                if (drawerState.isOpen) {
                    scope.launch { drawerState.close() }
                }

                finish()
            }

            SmartPocketTheme {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = { MenuOptionContainer(options) { scope.launch { drawerState.close() } } },
                    gesturesEnabled = false,
                    content = {
                        Scaffold(
                            topBar = {
                                AppTopBarMenu("Cuentas") {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            },
                            floatingActionButton = {
                                FloatingButton("Nueva Cuenta") {
                                    viewModel.toggleNewRecordDialog(
                                        true
                                    )
                                }
                            },
                            content = { innerPadding ->
                                LedgerScreen(innerPadding, viewModel)
                            }
                        )
                    }
                )
            }
        }
    }

    private fun checkCameraPermissionAndLaunch() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
}