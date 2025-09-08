package com.joshdev.smartpocket.ui.activities.photoai.subcomponents

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.joshdev.smartpocket.R

@Composable
fun PhotoShutter(
    bitmapImage: Bitmap?,
    onTake: () -> Unit, onFlash: () -> Unit, onReject: () -> Unit, onAccept: () -> Unit
) {
    Box(
        modifier = Modifier
            .absoluteOffset()
    ) {
        if (bitmapImage == null) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .clickable(onClick = { onFlash() })
                        .background(MaterialTheme.colorScheme.primary)
                        .border(
                            2.dp,
                            MaterialTheme.colorScheme.surfaceDim,
                            RoundedCornerShape(25.dp)
                        )
                ) {
                    Icon(
                        painterResource(R.drawable.flash),
                        contentDescription = "Toggle Flash",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .clickable(onClick = { onTake() })
                        .background(MaterialTheme.colorScheme.primary)
                        .border(
                            2.dp,
                            MaterialTheme.colorScheme.surfaceDim,
                            RoundedCornerShape(25.dp)
                        )
                ) {
                    Icon(
                        painterResource(R.drawable.camera),
                        contentDescription = "Take Image",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color.Transparent)
                ) {}
            }
        } else {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clickable(onClick = { onReject() })
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Icon(
                        painter = painterResource(R.drawable.close),
                        contentDescription = "Reject Image",
                        modifier = Modifier.size(50.dp),
                        tint = MaterialTheme.colorScheme.error
                    )
                }

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clickable(onClick = { onAccept() })
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Icon(
                        painter = painterResource(R.drawable.check),
                        contentDescription = "Accept Image",
                        modifier = Modifier.size(50.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
