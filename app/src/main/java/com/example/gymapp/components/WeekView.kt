package com.example.gymapp.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekView(
    highlightedDay: DayOfWeek,
    modifier: Modifier = Modifier,
    onDayClick: (DayOfWeek) -> Unit = {}
) {
    Card(
        modifier = modifier.padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DayOfWeek.entries.forEach { day ->
                val isSelected = day == highlightedDay

                val backgroundColor by animateColorAsState(
                    targetValue = if (isSelected) Color(0xFFB0C4DE) else Color.Transparent,
                    label = "DayBackground"
                )

                val textColor = if (isSelected) Color.Black else Color.Black
                val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal

                Box(
                    modifier = Modifier
                        .clickable { onDayClick(day) }
                        .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = day.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                        color = textColor,
                        fontSize = 12.sp,
                        fontWeight = fontWeight
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun WeekViewPreview() {
    WeekView(
        highlightedDay = DayOfWeek.WEDNESDAY,
        onDayClick = {}
    )
}
