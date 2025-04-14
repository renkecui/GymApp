package com.example.gymapp.HomeScreen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gymapp.MainActivity
import com.example.gymapp.testing.FakeExerciseViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val fakeViewModel = FakeExerciseViewModel().apply {
        // set up expected streak and currentDay here if needed
    }

    @Test
    fun homeScreen_displaysWelcomeAndWorkoutLabels() {
        composeTestRule.setContent {
            HomeScreen(
                navController = rememberNavController(),
//                navController = composeTestRule.activity.navController,
                viewModel = fakeViewModel
            )
        }

        //  welcome text
        composeTestRule
            .onNodeWithText("Hello, Welcome back!")
            .assertIsDisplayed()

        //  Check for today's workout label
        composeTestRule
            .onNodeWithText("Today's Workout")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("GYMAPP")
            .assertIsDisplayed()
    }
}
