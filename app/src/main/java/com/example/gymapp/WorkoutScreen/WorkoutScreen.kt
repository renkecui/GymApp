package com.example.gymapp.WorkoutScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.Components.TodayWorkout
import com.example.gymapp.Components.WeekView
import com.example.gymapp.ExerciseViewModel
import com.example.gymapp.testing.FakeExerciseViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.DayOfWeek


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutScreen(
    navController: NavHostController,
    viewModel: ExerciseViewModel
) {
    val currentDay by viewModel.currentDay.collectAsState()
    val dayDate by viewModel.dayDate.collectAsState()
    val highlightedDay = dayDate.dayOfWeek
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d")
    
    // Set the current day when the screen is opened
    LaunchedEffect(Unit) {
        viewModel.setDayDate(LocalDate.now())
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // Header row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { 
                viewModel.moveByWeek(forward = false)
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous Week")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("MY WEEK OF WORKOUTS", style = MaterialTheme.typography.titleLarge)
                Text(text = dayDate.format(formatter), fontSize = 14.sp)
            }
            IconButton(onClick = { 
                viewModel.moveByWeek(forward = true)
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next Week")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            WeekView(
                highlightedDay = highlightedDay, 
                modifier = Modifier.weight(1f),
                onDayClick = { dayOfWeek ->
                    viewModel.selectDayOfWeek(dayOfWeek)
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TodayWorkout(viewModel = viewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun WorkoutScreenPreview() {
    WorkoutScreen(
        navController = rememberNavController(),
        viewModel = com.example.gymapp.testing.FakeExerciseViewModel()
    )
}