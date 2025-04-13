package com.example.gymapp.Components

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.gymapp.ExerciseViewModel
import com.example.gymapp.data.ExerciseDbItem
import com.example.gymapp.data.WorkoutLog
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.gymapp.testing.FakeExerciseViewModel
import android.util.Log
import java.time.LocalDate
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogWorkout(viewModel: ExerciseViewModel) {
    // Get the list of exercises for the current day
    viewModel.getDayWorkoutList()
    val selectedExercises by viewModel.selectedExercises.collectAsState()
    val workoutLogs by viewModel.workoutLogs.collectAsState()
    val workoutNotes by viewModel.workoutNotes.collectAsState()
    val dayDate by viewModel.dayDate.collectAsState()
    var dateInput by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var notesText by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    // Group exercises by category
    val exercisesByCategory = remember(selectedExercises) {
        selectedExercises.groupBy { it.bodyPart }
    }

    // Load workout logs and notes when the composable is first displayed or when the date changes
    LaunchedEffect(dayDate) {
        Log.d("LogWorkout", "LaunchedEffect triggered for date: $dayDate")
        coroutineScope.launch {
            try {
                viewModel.loadWorkoutLogsForDate(dayDate)
                viewModel.loadWorkoutNotesForDate(dayDate)
            } catch (e: Exception) {
                Log.e("LogWorkout", "Error loading data: ${e.message}")
            }
        }
    }

    // Update notes text when workoutNotes changes
    LaunchedEffect(workoutNotes) {
        Log.d("LogWorkout", "WorkoutNotes changed: ${workoutNotes?.notes ?: "null"}")
        notesText = workoutNotes?.notes ?: ""
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Date input row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = dateInput,
                    onValueChange = { 
                        dateInput = it
                        showError = false
                    },
                    label = { Text("Enter date (M/D)") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = showError
                )
                Button(
                    onClick = {
                        try {
                            val parts = dateInput.split("/")
                            if (parts.size == 2) {
                                val month = parts[0].toInt()
                                val day = parts[1].toInt()
                                val currentYear = LocalDate.now().year
                                val newDate = LocalDate.of(currentYear, month, day)
                                viewModel.setDayDate(newDate)
                                showError = false
                            } else {
                                showError = true
                            }
                        } catch (e: Exception) {
                            showError = true
                        }
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("Go")
                }
            }
            if (showError) {
                Text(
                    text = "Please enter a valid date in M/D format",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Notes section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Workout Notes",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = notesText,
                        onValueChange = { 
                            Log.d("LogWorkout", "Notes text changed: $it")
                            notesText = it
                            coroutineScope.launch {
                                try {
                                    viewModel.saveWorkoutNotes(it)
                                } catch (e: Exception) {
                                    Log.e("LogWorkout", "Error saving notes: ${e.message}")
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Add notes about your workout...") },
                        minLines = 3,
                        maxLines = 5
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedExercises.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No exercises planned for this day",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    exercisesByCategory.forEach { (category, exercises) ->
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp)
                                ) {
                                    // Category header
                                    Text(
                                        text = "${category.replaceFirstChar { it.uppercase() }} - ${exercises.size} ${if (exercises.size == 1) "exercise" else "exercises"}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )

                                    // Exercises in this category
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        exercises.forEach { exercise ->
                                            LogExerciseItem(
                                                exercise = exercise,
                                                viewModel = viewModel,
                                                workoutLog = workoutLogs[exercise.id]
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogExerciseItem(
    exercise: ExerciseDbItem,
    viewModel: ExerciseViewModel,
    workoutLog: WorkoutLog?
) {
    var reps by remember(workoutLog) { mutableStateOf(workoutLog?.reps?.toString() ?: "") }
    var weight by remember(workoutLog) { mutableStateOf(workoutLog?.weight?.toString() ?: "") }

    // Update state when workoutLog changes
    LaunchedEffect(workoutLog) {
        reps = workoutLog?.reps?.toString() ?: ""
        weight = workoutLog?.weight?.toString() ?: ""
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFA5D6A7))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = exercise.name.replaceFirstChar { it.uppercase() },
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = exercise.target.replaceFirstChar { it.uppercase() },
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = reps,
                onValueChange = { newValue ->
                    reps = newValue
                    if (newValue.isNotEmpty() && weight.isNotEmpty()) {
                        viewModel.saveWorkoutLog(
                            exerciseId = exercise.id,
                            reps = newValue.toIntOrNull() ?: 0,
                            weight = weight.toFloatOrNull() ?: 0f
                        )
                    }
                },
                label = { Text("Reps") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = weight,
                onValueChange = { newValue ->
                    weight = newValue
                    if (newValue.isNotEmpty() && reps.isNotEmpty()) {
                        viewModel.saveWorkoutLog(
                            exerciseId = exercise.id,
                            reps = reps.toIntOrNull() ?: 0,
                            weight = newValue.toFloatOrNull() ?: 0f
                        )
                    }
                },
                label = { Text("Weight (lbs)") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun LogWorkoutPreview() {
    LogWorkout(viewModel = FakeExerciseViewModel())
}