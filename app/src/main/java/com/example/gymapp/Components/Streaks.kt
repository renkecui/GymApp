package com.example.gymapp.Components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.DayOfWeek

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Streaks(count: Int, highlightedDay: DayOfWeek) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(10.dp)
    ) {
        Text(
            text = "Week 1",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 3.dp)
        )

        WeekView(
            highlightedDay = highlightedDay,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )


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
