package com.joshdev.smartpocket.src.activities.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.joshdev.smartpocket.src.activities.home.components.HomeScreen
import com.joshdev.smartpocket.src.components.AppBasicTopBar
import com.joshdev.smartpocket.src.components.HomeFloatingButton
import com.joshdev.smartpocket.src.database.entity.invoice.Invoice
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme
import com.joshdev.smartpocket.src.utils.AppUtils.showToast

class HomeActivity : ComponentActivity() {
    private val viewModel by viewModels<HomeViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.start(this@HomeActivity)

        setContent {
            val invoices by viewModel.invoices.collectAsState()

            SmartPocketTheme {
                Scaffold(
                    topBar = { AppBasicTopBar() },
                    floatingActionButton = {
                        HomeFloatingButton {
                            showToast(this, "New Invoice")

                            val inv = Invoice(
                                name = "Prueba",
                                author = "Joshua",
                                creationDate = "15-10-1991",
                                modificationDate = "15-10-1991",
                                total = 500.0
                            )

                            viewModel.addInvoice(inv)
                        }
                    },
                    content = { innerPadding -> HomeScreen(innerPadding, invoices) }
                )
            }
        }
    }
}