package com.example.gymapp.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.data.eDayOfWeek
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.layout.*
import androidx.compose.foundation.layout.* // This gives you Row, Column, etc.

@Composable
fun WeekView(highlightedDay: eDayOfWeek, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            eDayOfWeek.entries.forEach { day ->
                Box(
                    modifier = Modifier
                        .size(36.dp)
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
    }
}



@Preview(showBackground = true)
@Composable
private fun WeekViewPreviewT() {
    val dayOfWeek: eDayOfWeek = eDayOfWeek.TUESDAY

    Row(modifier = Modifier.fillMaxWidth()) {
        WeekView(
            highlightedDay = dayOfWeek,
            modifier = Modifier.weight(1f) // Now valid!
        )
    }
}
