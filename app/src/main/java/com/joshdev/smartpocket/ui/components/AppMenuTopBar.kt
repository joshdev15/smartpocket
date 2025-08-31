package com.joshdev.smartpocket.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
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
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.menu),
                        contentDescription = "arrow down",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.clickable( onClick = { onClick() })
                    )
                }
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

