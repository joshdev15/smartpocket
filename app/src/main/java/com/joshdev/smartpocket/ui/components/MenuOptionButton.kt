package com.joshdev.smartpocket.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuOptionButton(icon: Int, name: String, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 10.dp)
            .padding(horizontal = 30.dp)
            .clickable(onClick = { onClick() })
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = name,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(end = 10.dp)
        )
        AppText(name, color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp)
    }
}