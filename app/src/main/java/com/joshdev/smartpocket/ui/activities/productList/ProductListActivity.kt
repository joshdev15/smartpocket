package com.joshdev.smartpocket.ui.activities.productList

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import com.joshdev.smartpocket.ui.activities.productList.subcomponents.FloatingPanel
import com.joshdev.smartpocket.ui.activities.productList.subcomponents.ProductScreen
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme

class ProductListActivity : ComponentActivity() {
    val viewModel by viewModels<ProductListViewModel>()
    private var invoiceId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        intent.getStringExtra("invoiceId")?.let { invoiceId = it }
        viewModel.start(this, this@ProductListActivity, invoiceId)

        setContent {
            LaunchedEffect(viewModel.products.value) {
                var totalValue = 0.0
                viewModel.products.value.forEach {
                    totalValue += it.cost * it.quantity
                }
                viewModel.updateTransactionAmount(totalValue)
            }

            SmartPocketTheme {
                Scaffold(
                    topBar = { AppTopBarBasic("Productos de ${viewModel.ledgerTransaction.value?.name ?: ""}") },
                    floatingActionButton = {
                        FloatingPanel(
                            "Lectura con IA",
                            "Agregar Producto",
                            { viewModel.goToPhotoIA(invoiceId) },
                            { viewModel.toggleNewInvoiceDialog(true) },
                        )
                    },
                    content = { innerPadding -> ProductScreen(innerPadding, viewModel, invoiceId) }
                )
            }
        }
    }
}