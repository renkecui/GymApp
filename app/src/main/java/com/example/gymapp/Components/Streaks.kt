package com.example.gymapp.Components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.DayOfWeek

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Streaks(count: Int, highlightedDay: DayOfWeek) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // WeekView gets weighted space
            WeekView(
                highlightedDay = highlightedDay,
                modifier = Modifier.weight(4f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Streak info
            Column(
                modifier = Modifier
                    .widthIn(max = 70.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "$count DAY STREAK",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Keep it up!",
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodySmall
                )
            }

        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun StreaksPreviewM5() {
    val count: Int = 5
    val dayOfWeek = DayOfWeek.MONDAY
    Streaks(count, dayOfWeek)
}
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun StreaksPreviewT12() {
    val count: Int = 12
    val dayOfWeek = DayOfWeek.TUESDAY
    Streaks(count, dayOfWeek)
}
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun StreaksPreviewS182() {
    val count: Int = 182
    val dayOfWeek = DayOfWeek.SATURDAY
    Streaks(count, dayOfWeek)
}