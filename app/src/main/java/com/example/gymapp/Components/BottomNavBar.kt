package com.example.gymapp.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold

@Composable
fun BottomNavBar(selectedItem: String, onItemSelected: (String) -> Unit) {
    val items = listOf("Home", "Plan", "Workout", "Log")

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItem == item,
                onClick = { onItemSelected(item) },
                icon = {
                    when (item) {
                        "Home" -> Icon(Icons.Default.Home, contentDescription = "Home")
                        "Plan" -> Icon(Icons.Default.Edit, contentDescription = "Plan")
                        "Workout" -> Icon(Icons.AutoMirrored.Filled.DirectionsRun, contentDescription = "Workout")
                        "Log" -> Icon(Icons.Default.CalendarToday, contentDescription = "Log")
                    }
                },
                label = { Text(item) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomNavBarHome() {
    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedItem = "Home",
                onItemSelected = {}
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Text("Content here")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewBottomNavBarWorkout() {
    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedItem = "Workout",
                onItemSelected = {}
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Text("Content here")
        }
    }
}
