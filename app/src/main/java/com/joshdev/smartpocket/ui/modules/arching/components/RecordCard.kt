package com.joshdev.smartpocket.ui.modules.arching.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshdev.smartpocket.domain.arching.ArcRecord
import com.joshdev.smartpocket.ui.components.AppText

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecordCard(
    arcRecord: ArcRecord,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .border(2.dp, MaterialTheme.colorScheme.tertiaryContainer, RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.background)
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick() }
            )
            .padding(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.weight(1f)
        ) {
            AppText(
                text = arcRecord.dayName,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
            )
            AppText(
                text = "Semana: ${arcRecord.weekOfYear}",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 12.sp,
            )
            AppText(
                text = "Mes: ${arcRecord.monthOfYear}",
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 12.sp,
            )
        }
    }
}