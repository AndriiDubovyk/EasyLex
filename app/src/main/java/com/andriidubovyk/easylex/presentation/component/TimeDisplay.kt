package com.andriidubovyk.easylex.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.andriidubovyk.easylex.domain.model.Time

@Composable
fun TimeDisplay(
    modifier: Modifier = Modifier,
    time: Time,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .width(54.dp)
            .height(32.dp)
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(vertical = 2.dp, horizontal = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val hoursStr = "${if (time.hour < 10) "0" else ""}${time.hour}"
        Text(hoursStr)
        Text(":")
        val minutesStr = "${if (time.minute < 10) "0" else ""}${time.minute}"
        Text(minutesStr)
    }
}