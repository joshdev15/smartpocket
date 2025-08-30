package com.joshdev.smartpocket.ui.activities.records

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.ui.components.AppBasicTopBar
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme

class RecordsActivity : ComponentActivity() {
    private var recordId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        intent.getIntExtra("recordId", -1)?.let {
            recordId = it
        }

        setContent {
            SmartPocketTheme {
                Scaffold(
                    topBar = { AppBasicTopBar() },
                    content = { innerPadding ->
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .padding(horizontal = 10.dp)
                        ) {
                            Text(text = "Vista Record de $recordId")
                        }
                    }
                )
            }
        }
    }
}