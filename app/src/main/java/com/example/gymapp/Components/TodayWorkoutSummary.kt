package com.example.gymapp.Components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import com.example.gymapp.ExerciseViewModel
import com.example.gymapp.testing.FakeExerciseViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodayWorkoutSummaryCard(viewModel: ExerciseViewModel) {
    var isExpanded by remember { mutableStateOf(false) }
    val currentDay by viewModel.currentDay.collectAsState()
    val exercisesForToday = remember(currentDay) {
        viewModel._exercisesByDate[currentDay] ?: emptyList()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFB0C4DE)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.FitnessCenter,
                        contentDescription = "Dumbbell",
                        modifier = Modifier.size(32.dp),
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Exercises",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = Color.Black
                )
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                if (exercisesForToday.isEmpty()) {
                    Text(
                        text = "No exercises planned for today",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 44.dp)
                    )
                } else {
                    Column(
                        modifier = Modifier.padding(start = 44.dp)
                    ) {
                        exercisesForToday.forEach { exercise ->
                            Text(
                                text = exercise.name.replaceFirstChar { it.uppercase() },
                                color = Color.Black,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewTodayWorkoutCard() {
    TodayWorkoutSummaryCard(viewModel = FakeExerciseViewModel())
}
