package com.example.gymapp.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.data.eDayOfWeek


@Composable
fun Streaks(count: Int, highlightedDay: eDayOfWeek) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4
                .dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Days of the week
            Row {
                eDayOfWeek.entries.forEach{ day ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(28.dp)
                            .background(
                                color = if (day == highlightedDay) Color.Yellow else Color.Transparent,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day.shortName,
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Streak Info
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 8.dp
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "$count DAY STREAK", fontWeight = FontWeight.Bold)
                    Text(text = "Keep it up!", fontSize = 12.sp)
                }
            }

        }
    }
}

@Preview
@Composable
private fun StreaksPreviewM5() {
    val count: Int = 5
    val dayOfWeek: eDayOfWeek = eDayOfWeek.MONDAY
    Streaks(count, dayOfWeek)
}

@Preview
@Composable
private fun StreaksPreviewT12() {
    val count: Int = 12
    val dayOfWeek: eDayOfWeek = eDayOfWeek.TUESDAY
    Streaks(count, dayOfWeek)
}
@Preview
@Composable
private fun StreaksPreviewS182() {
    val count: Int = 182
    val dayOfWeek: eDayOfWeek = eDayOfWeek.SATURDAY
    Streaks(count, dayOfWeek)
}