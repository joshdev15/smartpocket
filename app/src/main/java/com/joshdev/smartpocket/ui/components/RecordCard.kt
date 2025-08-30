package com.joshdev.smartpocket.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshdev.smartpocket.domain.models.GeneralRecord
import com.joshdev.smartpocket.ui.utils.UiUtils.formatDate

@Composable
fun RecordCard(record: GeneralRecord, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .clickable(onClick = { onClick() })
            .border(2.dp, MaterialTheme.colorScheme.tertiaryContainer, RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {
        Column {
            Text(
                text = record.name,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 18.sp,
                ),
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "ID: ${record.id}",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp,
                    )
                )
                Text(
                    text = formatDate(record.creationDate),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 12.sp,
                    )
                )
            }
        }
    }
}