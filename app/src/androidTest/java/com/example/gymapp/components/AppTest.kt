package com.example.gymapp.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gymapp.MainActivity
import com.example.gymapp.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.assertIsDisplayed


@RunWith(AndroidJUnit4::class)
class AppTest {

    // Launches MainActivity for testing
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun gymAppIsDisplayed() {
        // Checks that the composable with the test tag "GymAppRoot" is displayed
        composeTestRule.onNodeWithTag("GymAppRoot")
            .assertIsDisplayed()
    }
}

//    @Test
//    fun app_canNavigateToAllScreens() {
//        // Check app launches at HOME
//        composeTestRule.onNodeWithText("home").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Hello, Welcome back!").assertIsDisplayed()
//
//        // Navigate to Plan
//        composeTestRule.onNodeWithText("plan").performClick().assertIsDisplayed()
//        composeTestRule.onNodeWithText("Planner").assertIsDisplayed()
//
//        // Navigate to Workout
//        composeTestRule.onNodeWithText("workout").performClick().assertIsDisplayed()
//        composeTestRule.onNodeWithText("MY WEEK OF WORKOUTS").assertIsDisplayed()
//
//        // Navigate to Log
//        composeTestRule.onNodeWithText("log").performClick().assertIsDisplayed()
//        composeTestRule.onNodeWithText("Log").assertIsDisplayed()
//    }
//}
