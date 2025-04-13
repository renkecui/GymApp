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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.Components.BodyPartExerciseList
import com.example.gymapp.Components.BottomNavBar
import com.example.gymapp.HomeScreen.HomeScreen
import com.example.gymapp.Log.LogScreen
import com.example.gymapp.Plan.PlanScreen
import com.example.gymapp.WorkoutScreen.WorkoutScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GymApp() {
    val navController = rememberNavController()
    val selectedTab = remember { mutableStateOf("home") }
    val context = LocalContext.current
    val viewModel: ExerciseViewModel = viewModel()
    viewModel.initialize(context)

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.primaryContainer,
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
            composable("home") { HomeScreen(navController, viewModel) }
            composable("plan") { PlanScreen(navController, viewModel) }
            composable("workout") { WorkoutScreen(navController, viewModel) }
            composable("log") { LogScreen(navController, viewModel) }


            // nested route example
            composable("plan/{part}") { backStackEntry ->
                val part = backStackEntry.arguments?.getString("part") ?: ""
                BodyPartExerciseList(
                    bodyPart = part,
                    viewModel = viewModel,
                    navController = navController
                )
            }
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