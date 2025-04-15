package com.example.gymapp.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.text.font.FontWeight

@Composable
fun BottomNavBar(selectedItem: String, onItemSelected: (String) -> Unit) {
    val items = listOf("home", "plan", "workout", "log")

    NavigationBar {
        items.forEach { item ->
            val isSelected = selectedItem == item
            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemSelected(item) },
                icon = {
                    when (item) {
                        "home" -> Icon(Icons.Default.Home, contentDescription = "Home")
                        "plan" -> Icon(Icons.Default.Edit, contentDescription = "Plan")
                        "workout" -> Icon(Icons.AutoMirrored.Filled.DirectionsRun, contentDescription = "Workout")
                        "log" -> Icon(Icons.Default.CalendarToday, contentDescription = "Log")
                    }
                },
                label = {
                    Text(
                        text = item.replaceFirstChar { it.uppercaseChar() },
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = .2f)
                )
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
                selectedItem = "home",
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
                selectedItem = "workout",
                onItemSelected = {}
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Text("Content here")
        }
    }
}
