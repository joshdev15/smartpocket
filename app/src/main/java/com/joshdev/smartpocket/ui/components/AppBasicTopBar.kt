package com.joshdev.smartpocket.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBasicTopBar(section: String) {
    TopAppBar(
        title = {
            Column {
                AppText("Smart Pocket", fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
                AppText(section, color = MaterialTheme.colorScheme.onBackground, fontSize = 12.sp)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        modifier = Modifier
            .shadow(10.dp, RoundedCornerShape(30.dp))
            .clip(RoundedCornerShape(30.dp))
    )
}