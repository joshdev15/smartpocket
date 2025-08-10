package com.joshdev.smartpocket.activities.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshdev.smartpocket.database.entity.invoice.Invoice
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme

class HomeActivity : ComponentActivity() {
    private val viewModel by viewModels<HomeViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel.start(this@HomeActivity)

        setContent {
            setContent {
                SmartPocketTheme {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("Smart Pocket") },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                                )
                            )
                        },
                        bottomBar = {
                            BottomAppBar(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Button(onClick = { viewModel.deleteAllInvoices() }) {
                                        Text("Delete All")
                                    }

                                    Button(
                                        onClick = {
                                            viewModel.addInvoice(
                                                Invoice(
                                                    name = "Test",
                                                    author = "Test",
                                                    creationDate = "Test",
                                                    modificationDate = "Test",
                                                    productId = 1,
                                                    total = 1.0
                                                )
                                            )
                                        }
                                    ) {
                                        Text("Add")
                                    }
                                }
                            }
                        }
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(innerPadding)
                        ) {
                            val invoices by viewModel.invoices.collectAsState()

                            LazyColumn(
                                modifier = Modifier.padding(horizontal = 10.dp)
                            ) {
                                itemsIndexed(invoices) { idx, it ->
                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(if (idx == 0) 10.dp else 0.dp)
                                    )

                                    Row(
                                        modifier = Modifier
                                            .padding(bottom = 10.dp)
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(MaterialTheme.colorScheme.primaryContainer)
                                            .padding(20.dp)
                                    ) {
                                        Text(
                                            text = "${it.id} ${it.name} - ${it.author}",
                                            style = TextStyle(
                                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                fontSize = 18.sp,
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}