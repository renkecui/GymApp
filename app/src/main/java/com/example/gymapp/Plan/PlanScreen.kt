package com.example.gymapp.Plan

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.Components.WeekView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.Components.BodyPartExerciseList
import com.example.gymapp.Components.ExerciseCategories
import com.example.gymapp.ExerciseViewModel
import com.example.gymapp.testing.FakeExerciseViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.DayOfWeek

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlanScreen(
    navController: NavHostController,
    viewModel: ExerciseViewModel
) {
    val exerciseCategories by viewModel.categories.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val currentDay by viewModel.currentDay.collectAsState()
    val dayDate by viewModel.dayDate.collectAsState()
    val highlightedDay = dayDate.dayOfWeek
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d")

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
                Text("Planner", style = MaterialTheme.typography.titleLarge)
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

        ExerciseCategories(
            exerciseDbList = exerciseCategories,
            viewModel = viewModel,
            navController = navController
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun PlanScreenPreview() {
    val highlightedDay = LocalDate.now()
    val fakeViewModel = remember { FakeExerciseViewModel() }

    PlanScreen(
        navController = rememberNavController(),
        viewModel = fakeViewModel
    )
}
