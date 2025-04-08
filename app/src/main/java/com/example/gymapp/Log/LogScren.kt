package com.example.gymapp.Log

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.Components.WeekView
import com.example.gymapp.Plan.PlanScreen
import com.example.gymapp.data.eDayOfWeek


@Composable
fun LogScreen(navController: NavHostController, highlightedDay: eDayOfWeek) {
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

    }
}



@Preview(showBackground = true)
@Composable
private fun LogScreenPreview() {
    // Provide a fake navController for preview
    val highlightedDay = eDayOfWeek.MONDAY

    LogScreen(navController = rememberNavController(), highlightedDay)
}