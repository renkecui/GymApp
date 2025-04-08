package com.example.gymapp.Plan

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.Components.WeekView
import com.example.gymapp.data.eDayOfWeek
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlanScreen(
    navController: NavHostController,
    highlightedDay: eDayOfWeek
) {
    val exercises = listOf("Abs", "Back", "Biceps", "Legs", "Cardio", "Chest", "Triceps")
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
                // TODO Implement dynamic day tracking
                // Show today's date?
                Text("Week _ - _", fontSize = 14.sp)
            }
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            WeekView(highlightedDay = highlightedDay, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Exercise buttons in a 2-column grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            items(exercises) { exercise ->
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
}

@Preview(showBackground = true)
@Composable
private fun PlanScreenPreview() {
    // Provide a fake navController for preview
    val highlightedDay = eDayOfWeek.MONDAY

    PlanScreen(navController = rememberNavController(), highlightedDay)
}