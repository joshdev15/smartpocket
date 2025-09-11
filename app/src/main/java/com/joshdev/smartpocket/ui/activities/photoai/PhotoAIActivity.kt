package com.joshdev.smartpocket.ui.activities.photoai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.joshdev.smartpocket.ui.activities.photoai.subcomponents.PhotoNavigator
import com.joshdev.smartpocket.ui.components.AppBasicTopBar
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme

class PhotoAIActivity : ComponentActivity() {
    private val viewModel by viewModels<PhotoAIViewModel>()
    private var invoiceId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        intent.getIntExtra("invoiceId", -1).let {
            invoiceId = it
        }

        setContent {
            val navController = rememberNavController()

            viewModel.start(this, this@PhotoAIActivity, invoiceId, navController)

            SmartPocketTheme {
                Scaffold(
                    topBar = {
                        AppBasicTopBar("Lectura con IA")
                    },
                    content = { innerPadding ->
                        PhotoNavigator(
                            viewModel,
                            navController,
                            innerPadding,
                        )
                    }
                )
            }
        }
    }
}