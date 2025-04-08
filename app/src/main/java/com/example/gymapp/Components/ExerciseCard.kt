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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import com.example.gymapp.data.Difficulty
import com.example.gymapp.data.Exercise


@Composable
fun ExerciseCard(
    exercise: Exercise
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // TODO placeholder icon
            Icon(
                imageVector = Icons.Default.FitnessCenter,
                contentDescription = "Dumbbell",
                modifier = Modifier.size(32.dp)
            )

            // Title centered in the remaining space
            Box(
                modifier = Modifier
                    .weight(0.8f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewExerciseCardPushUp() {
    val pushUps = Exercise(
        id = 1,
        name = "Push-ups",
        description = "A basic upper-body strength exercise targeting the chest and triceps.",
        muscleGroup = "Chest",
        durationMinutes = 5,
        difficulty = Difficulty.MEDIUM,
        reps = 15
    )

    ExerciseCard(pushUps)
}
