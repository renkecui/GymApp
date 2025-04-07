package com.example.gymapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.HomeScreen.HomeScreen


@Composable
fun GymApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("Home") { HomeScreen(navController) }
    }
}
