package com.joshdev.smartpocket.ui.activities.invoiceList

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import com.joshdev.smartpocket.ui.activities.invoiceList.subcomponents.InvoiceScreen
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
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
                    topBar = { AppTopBarBasic("Facturas de ${viewModel.record.value?.name}") },
                    floatingActionButton = {
                        FloatingButton("Nueva Factura") { viewModel.toggleNewInvoiceDialog(true) }
                    },
                    content = { innerPadding -> InvoiceScreen(innerPadding, viewModel, recordId) }
                )
            }
        }
    }
}