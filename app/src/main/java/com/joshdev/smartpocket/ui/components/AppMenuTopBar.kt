package com.joshdev.smartpocket.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshdev.smartpocket.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppMenuTopBar(section: String, onClick: () -> Unit) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    AppText(
                        "Smart Pocket",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    AppText(
                        section,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 12.sp
                    )
                }
            }
        },
        actions = {
            Image(
                painter = painterResource(id = R.drawable.smartpocket),
                contentDescription = "arrow down",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(25.dp)
                    .clickable(onClick = { onClick() })
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceDim,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        modifier = Modifier.clip(RoundedCornerShape(20.dp))
    )
}

