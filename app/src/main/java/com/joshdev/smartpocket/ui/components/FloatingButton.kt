package com.joshdev.smartpocket.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshdev.smartpocket.R

@Composable
fun FloatingButton(label: String? = null, onClick: () -> Unit) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        onClick = { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
        ) {
            label?.let {
                if (label != "") {
                    AppText(text = label, color = MaterialTheme.colorScheme.onPrimaryContainer, fontSize = 13.sp)
                }
            }
            Icon(
                painter = painterResource(R.drawable.plus),
                contentDescription = "Floating button",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = if (label != null && label != "") 10.dp else 0.dp)
            )
        }
    }
}