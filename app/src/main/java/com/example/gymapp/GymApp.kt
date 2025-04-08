package com.example.gymapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.Components.BottomNavBar
import com.example.gymapp.HomeScreen.HomeScreen
import com.example.gymapp.Log.LogScreen
import com.example.gymapp.Plan.PlanScreen
import com.example.gymapp.WorkoutScreen.WorkoutScreen


@Composable
fun GymApp() {
    val navController = rememberNavController()
    var selectedTab = remember { mutableStateOf("Home") }

    Scaffold(
        bottomBar = {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            BottomNavBar(
                selectedItem = selectedTab.value,
                onItemSelected = { selectedTab.value = it }
            )
        }
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("plan") { PlanScreen(navController) }
            composable("workout") { WorkoutScreen(navController) }
            composable("log") { LogScreen(navController) }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GymAppPreview() {
    // Provide a fake navController for preview
    GymApp()
}