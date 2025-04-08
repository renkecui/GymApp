package com.example.gymapp

import android.os.Build
import androidx.annotation.RequiresApi
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
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GymApp() {
    val navController = rememberNavController()
    val selectedTab = remember { mutableStateOf("home") }
    val currentDate = LocalDate.now()
//    val formatter = DateTimeFormatter.ofPattern("MMM dd")
//    val formattedDate = currentDate.format(formatter)
//    val dayOfW = currentDate.dayOfWeek


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
            composable("home") { HomeScreen(navController, currentDate) }
            composable("plan") { PlanScreen(navController, currentDate) }
            composable("workout") { WorkoutScreen(navController, currentDate) }
            composable("log") { LogScreen(navController, currentDate) }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun GymAppPreview() {
    // Provide a fake navController for preview
    GymApp()
}