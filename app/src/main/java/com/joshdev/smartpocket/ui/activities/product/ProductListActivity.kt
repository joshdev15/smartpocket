package com.joshdev.smartpocket.ui.activities.product

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import com.joshdev.smartpocket.ui.activities.product.subcomponents.FloatingPanel
import com.joshdev.smartpocket.ui.activities.product.subcomponents.ProductScreen
import com.joshdev.smartpocket.ui.components.AppBasicTopBar
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme

class ProductListActivity : ComponentActivity() {
    val viewModel by viewModels<ProductListViewModel>()
    private var invoiceId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getIntExtra("invoiceId", -1)?.let {
            invoiceId = it
        }

        viewModel.start(this, this@ProductListActivity, invoiceId)
        viewModel.loadProducts()

        setContent {
            SmartPocketTheme {
                Scaffold(
                    topBar = { AppBasicTopBar() },
                    floatingActionButton = {
                        FloatingPanel(
                            "Lectura con IA",
                            "Agregar Producto",
                            { },
                            { viewModel.toggleNewInvoiceDialog(true) },
                        )
                    },
                    content = { innerPadding -> ProductScreen(innerPadding, viewModel, invoiceId) }
                )
            }
        }
    }
}