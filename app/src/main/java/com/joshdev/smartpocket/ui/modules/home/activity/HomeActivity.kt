package com.joshdev.smartpocket.ui.modules.home.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.models.HomeOption
import com.joshdev.smartpocket.ui.modules.arching.activity.ArchingActivity
import com.joshdev.smartpocket.ui.modules.currency.activity.CurrencyActivity
import com.joshdev.smartpocket.ui.modules.home.components.HomeScreen
import com.joshdev.smartpocket.ui.modules.ledger.activity.LedgerActivity
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme
import com.joshdev.smartpocket.ui.utils.UiUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {
    private val viewModel by viewModels<HomeViewModel>()
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            viewModel.setCameraAllowed(isGranted)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        checkCameraPermissionAndLaunch()

        val options = UiUtils.getHomeOptions()

        setContent {
            BackHandler {
                when {
                    viewModel.allowExit.value -> {
                        finish()
                    }

                    else -> {
                        UiUtils.showToast(this@HomeActivity, "Click nuevamente para salir")
                        viewModel.setAllowExit(true)
                        lifecycleScope.launch {
                            delay(2000)
                            viewModel.setAllowExit(false)
                        }
                    }
                }
            }

            SmartPocketTheme {
                Scaffold(
                    topBar = { AppTopBarBasic("Inicio") },
                    content = { innerPadding ->
                        HomeScreen(innerPadding, options) {
                            goToOption(it)
                        }
                    }
                )
            }
        }
    }

    fun goToOption(optionId: HomeOption.IDs) {
        val goTo = when (optionId) {
            HomeOption.IDs.LEDGERS -> {
                Intent(this, LedgerActivity::class.java)
            }

            HomeOption.IDs.ARCHING -> {
                Intent(this, ArchingActivity::class.java)
            }

            HomeOption.IDs.COINS -> {
                Intent(this, CurrencyActivity::class.java)
            }
        }

        startActivity(goTo)
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