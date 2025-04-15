package com.example.gymapp.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.example.gymapp.ExerciseViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BodyPartExerciseList(
    bodyPart: String,
    viewModel: ExerciseViewModel,
    navController: NavHostController
) {
    val currentDate by viewModel.dayDate.collectAsState()
    val selectedExercisesForDay by viewModel.selectedExercises.collectAsState()
    val bodyPartExerciseList by viewModel.bodyPartExercises.collectAsState()

    // Load the workout list and body part exercises when the composable is first displayed
    LaunchedEffect(Unit) {
        viewModel.getDayWorkoutList()
        viewModel.getBodyPartExercises(bodyPart)
    }

    // Reload body part exercises when the body part changes
    LaunchedEffect(bodyPart) {
        viewModel.getBodyPartExercises(bodyPart)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to Categories"
                )
            }
            Text(
                text = bodyPart.replaceFirstChar { it.uppercase() },
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            // Empty box for symmetry
            Box(modifier = Modifier.size(48.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            items(bodyPartExerciseList) { exercise ->
                ExerciseItemCard(
                    exercise = exercise,
                    isSelected = selectedExercisesForDay.any { it.id == exercise.id },
                    onToggleSelect = { clickedExercise ->
                        if (selectedExercisesForDay.any { it.id == clickedExercise.id }) {
                            // Remove from database
                            viewModel.removeSelectedExercise(clickedExercise, currentDate)
                        } else {
                            // Save to database
                            viewModel.saveSelectedExercise(clickedExercise, currentDate)
                        }
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun BodyPartExerciseListPreview() {
    BodyPartExerciseList(
        bodyPart = "back",
        viewModel = com.example.gymapp.testing.FakeExerciseViewModel(),
        navController = rememberNavController()
    )
}
