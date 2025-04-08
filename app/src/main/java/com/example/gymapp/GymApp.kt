package com.example.gymapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.Components.BottomNavBar
import com.example.gymapp.HomeScreen.HomeScreen
import com.example.gymapp.Log.LogScreen
import com.example.gymapp.Plan.PlanScreen
import com.example.gymapp.WorkoutScreen.WorkoutScreen
import com.example.gymapp.data.eDayOfWeek

@Composable
fun GymApp() {
    val navController = rememberNavController()
    val selectedTab = remember { mutableStateOf("home") }
    val highlightedDay = eDayOfWeek.MONDAY

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                BottomNavBar(
                    selectedItem = selectedTab.value,
                    onItemSelected = { route ->
                        selectedTab.value = route
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(navController, highlightedDay) }
            composable("plan") { PlanScreen(navController, highlightedDay) }
            composable("workout") { WorkoutScreen(navController, highlightedDay) }
            composable("log") { LogScreen(navController, highlightedDay) }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun GymAppPreview() {
    // Provide a fake navController for preview
    GymApp()
}