package com.example.gymapp.Components

import androidx.compose.runtime.Composable

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.gymapp.Log.LogScreen


@Composable
fun LogWorkout(
//    muscleGroup: String = "Abs",
//    exercises: List<Exercise> = sampleExercises
) {
    Column(modifier = Modifier.padding(16.dp)) {

        // Title: Day
        Text(
            text = "Today's workout",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Muscle group + count + notes button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Today's muscle Group",
//                text = "$muscleGroup – ${exercises.size} exercises",
                style = MaterialTheme.typography.bodyLarge
            )
//            Button(onClick = { /* TODO: Open notes dialog */ }) {
//                Text("Notes")
//            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // List of exercise logs
//        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
//            items(exercises) { exercise ->
//                ExerciseLogCard(exercise)
//            }
//        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun LogWorkoutPreview() {
    // Provide a fake navController for preview
    val highlightedDay = LocalDate.now()

    LogWorkout()
}