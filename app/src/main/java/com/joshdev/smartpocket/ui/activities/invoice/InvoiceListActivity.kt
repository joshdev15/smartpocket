package com.joshdev.smartpocket.ui.activities.invoice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.ui.activities.invoice.subcomponents.InvoiceScreen
import com.joshdev.smartpocket.ui.components.AppBasicTopBar
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme

class InvoiceListActivity : ComponentActivity() {
    private val viewModel by viewModels<InvoiceListViewModel>()
    private var recordId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        intent.getIntExtra("recordId", -1)?.let {
            recordId = it
        }

        viewModel.start(this, this@InvoiceListActivity, recordId)

        setContent {
            SmartPocketTheme {
                Scaffold(
                    topBar = { AppBasicTopBar() },
                    floatingActionButton = {
                        FloatingButton("Nueva Factura") { viewModel.toggleNewInvoiceDialog(true) }
                    },
                    content = { innerPadding -> InvoiceScreen(innerPadding, viewModel, recordId) }
                )
            }
        }
    }
}