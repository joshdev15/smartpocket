package com.joshdev.smartpocket.ui.activities.archingProducts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import com.joshdev.smartpocket.ui.activities.archingProducts.subcomponents.ArchingProductScreen
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.components.FloatingButton
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme

class ArchingProductsActivity : ComponentActivity() {
    private val viewModel by viewModels<ArchingProductViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.start(this, this@ArchingProductsActivity)

        setContent {
            SmartPocketTheme {
                Scaffold(
                    topBar = { AppTopBarBasic("Productos de Cierre") },
                    floatingActionButton = {
                        FloatingButton(
                            "Agregar Producto",
                        ) {
                            viewModel.toggleNewProductDialog(true)
                        }
                    },
                    content = { innerPadding ->
                        ArchingProductScreen(
                            innerPadding,
                            viewModel
                        )
                    }
                )
            }
        }
    }
}