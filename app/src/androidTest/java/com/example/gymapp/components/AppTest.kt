package com.example.gymapp.components

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gymapp.MainActivity
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AppTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun app_launches() {
        rule.waitForIdle()
        // Check app launches at the correct destination
        rule.onNodeWithText("HOME").assertIsDisplayed()
        rule.onNodeWithText("Hello, Welcome back!").assertIsDisplayed()
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
