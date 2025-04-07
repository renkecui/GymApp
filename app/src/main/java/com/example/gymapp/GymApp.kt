package com.example.gymapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.HomeScreen.HomeScreen
import com.example.gymapp.Log.LogScreen
import com.example.gymapp.Plan.PlanScreen
import com.example.gymapp.WorkoutScreen.WorkoutScreen


@Composable
fun GymApp() {
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
