package com.example.gymapp.Components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.gymapp.ExerciseViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.example.gymapp.testing.FakeExerciseViewModel
import com.example.gymapp.data.ExerciseDbItem
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BodyPartExerciseList(
    bodyPart: String,
    viewModel: ExerciseViewModel,
    navController: NavHostController
) {
    val currentDate by viewModel.dayDate.collectAsState()
    
    // Get the list of exercises for the current day
    viewModel.getDayWorkoutList()
    val selectedExercisesForDay by viewModel.selectedExercises.collectAsState()
    
    Log.d("BodyPartExerciseList", "list to get: $bodyPart")
    viewModel.getBodyPartExercises(bodyPart)
    val bodyPartExerciseList by viewModel.bodyPartExercises.collectAsState()

    Log.d("BodyPartExerciseList", "list of exercises: $bodyPartExerciseList")

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
        viewModel = FakeExerciseViewModel(),
        navController = rememberNavController()
    )
}
