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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.Components.WeekView
import com.example.gymapp.data.eDayOfWeek
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.*
import com.example.gymapp.Components.ExerciseCategories
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlanScreen(
    navController: NavHostController,
    currentDay: LocalDate
) {
    val highlightedDay = currentDay.dayOfWeek
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
    var selectedExercises by remember { mutableStateOf(setOf<String>()) }
//    val dateToday = DateTime.now

    Column(modifier = Modifier.padding(16.dp)) {

        // Header row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Planner", style = MaterialTheme.typography.titleLarge)
                // Show Today's Date
                val currentDate = LocalDate.now()
                val formatter = DateTimeFormatter.ofPattern("MMM dd")
                val formattedDate = currentDate.format(formatter)
                Text(text = formattedDate, fontSize = 14.sp)
            }
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            WeekView(highlightedDay = highlightedDay, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        ExerciseCategories(exerciseCategories)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun PlanScreenPreview() {
    // Provide a fake navController for preview
    val highlightedDay = LocalDate.now()


    PlanScreen(navController = rememberNavController(), highlightedDay)
}