package com.example.gymapp.Components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun ExerciseCategories(exerciseCategories: List<String>) {
    var selectedExercises by remember { mutableStateOf(setOf<String>()) }

    // Exercise buttons in a 2-column grid
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        items(exerciseCategories) { exercise ->
            val isSelected = selectedExercises.contains(exercise)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isSelected) Color(0xFFFFF176) else Color.LightGray)
                    .clickable {
                        selectedExercises = selectedExercises.toMutableSet().apply {
                            if (contains(exercise)) remove(exercise) else add(exercise)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = exercise,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExerciseCategories() {
    val exerciseCategories = listOf(
        "Abs",
        "Back",
        "Biceps",
        "Triceps",
        "Chest",
        "Shoulders",
        "Legs",
        "Glutes",
        "Cardio",
        "Core",
        "Full Body",
        "Arms",
        "Calves",
        "Forearms",
        "Neck",
        "Mobility",
        "Balance",
        "Stretching"
    )
    ExerciseCategories(exerciseCategories)
}
