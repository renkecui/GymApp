package com.example.gymapp.Components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.DirectionsRun

@Composable
fun BottomNavBar(selectedItem: String, onItemSelected: (String) -> Unit) {
    val items = listOf("home", "plan", "workout", "log")

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItem == item,
                onClick = { onItemSelected(item) },
                icon = {
                    when (item) {
                        "home" -> Icon(Icons.Default.Home, contentDescription = "Home")
                        "plan" -> Icon(Icons.Default.Edit, contentDescription = "Plan")
                        "workout" -> Icon(Icons.AutoMirrored.Filled.DirectionsRun, contentDescription = "Workout")
                        "log" -> Icon(Icons.Default.CalendarToday, contentDescription = "Log")
                    }
                },
                label = { Text(item.replaceFirstChar { it.uppercaseChar() }) } // Show label as "Home", etc.
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
