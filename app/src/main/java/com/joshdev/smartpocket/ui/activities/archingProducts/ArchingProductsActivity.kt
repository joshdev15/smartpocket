package com.joshdev.smartpocket.ui.activities.archingProducts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import com.joshdev.smartpocket.ui.components.AppText
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme

class ArchingProductsActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SmartPocketTheme {
                Scaffold(
                    topBar = {
                        AppTopBarBasic("Productos de Cierre")
                    },
                    content = {
                        AppText("Productos de Cierre")
                    }
                )
            }
        }
    }
}