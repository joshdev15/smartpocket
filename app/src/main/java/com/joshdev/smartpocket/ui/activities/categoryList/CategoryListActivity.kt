package com.joshdev.smartpocket.ui.activities.categoryList

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import com.joshdev.smartpocket.ui.activities.categoryList.subcomponents.CategoryListScreen
import com.joshdev.smartpocket.ui.components.AppTopBarBasic
import com.joshdev.smartpocket.ui.theme.SmartPocketTheme

class CategoryListActivity: ComponentActivity()  {
    val viewModel by viewModels<CategoryListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SmartPocketTheme {
                Scaffold(
                    topBar = { AppTopBarBasic("Categorías") },
                    content = { innerPadding -> CategoryListScreen(innerPadding, viewModel) }
                )
            }
        }
    }
}