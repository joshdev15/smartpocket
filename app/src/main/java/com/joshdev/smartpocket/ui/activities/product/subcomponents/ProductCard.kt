package com.joshdev.smartpocket.ui.activities.product.subcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joshdev.smartpocket.domain.models.Product
import com.joshdev.smartpocket.ui.components.AppText

@Composable
fun ProductCard(product: Product /*, onClick: () -> Unit */) {
    Row(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
//            .clickable(onClick = { onClick() })
            .border(2.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .padding(20.dp)
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AppText(
                    text = product.name,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Medium,
                )
                AppText(
                    text = "ID: ${product.id}",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 12.sp,
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AppText(
                    text = "$${product.cost}",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold,
                )
                AppText(
                    text = "Cant.: ${product.quantity}",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 12.sp,
                )
            }
        }
    }
}