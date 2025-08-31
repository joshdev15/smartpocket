package com.joshdev.smartpocket.ui.activities.productList.subcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import com.joshdev.smartpocket.ui.components.AppText

@Composable
fun FloatingPanel(firstLabel: String, secondLabel: String, onFirstClick: () -> Unit, onSecondClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom,
    ) {
        FloatingActionButton(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            onClick = { onFirstClick() }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
            ) {
                AppText(text = firstLabel, color = MaterialTheme.colorScheme.onPrimaryContainer, fontSize = 13.sp)
                Icon(
                    painter = painterResource(R.drawable.photo_ai),
                    contentDescription = "New Invoice",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        FloatingActionButton(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            onClick = { onSecondClick() }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
            ) {
                AppText(text = secondLabel, color = MaterialTheme.colorScheme.onPrimaryContainer, fontSize = 13.sp)
                Icon(
                    painter = painterResource(R.drawable.plus),
                    contentDescription = "New Invoice",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}
