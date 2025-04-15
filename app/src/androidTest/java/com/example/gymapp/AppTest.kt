//package com.example.gymapp
//
//
//import androidx.compose.ui.test.assertIsDisplayed
//import androidx.compose.ui.test.junit4.createAndroidComposeRule
//import androidx.compose.ui.test.onNodeWithText
//import androidx.compose.ui.test.performClick
//import com.example.gymapp.MainActivity
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.compose.ui.test.assertIsDisplayed
//import androidx.compose.ui.test.junit4.createAndroidComposeRule
//import androidx.compose.ui.test.onNodeWithText
//import androidx.compose.ui.test.performClick
//
//
//@RunWith(AndroidJUnit4::class)
//class AppTest {
//
//    @get:Rule
//    val composeTestRule = createAndroidComposeRule<MainActivity>()
//
//    @Test
//    fun app_launches() {
//        // Check app launches at the correct destination
//        composeTestRule.onNodeWithText("HOME").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Hello, Welcome back!").assertIsDisplayed()
//    }
//}
//
////    @Test
////    fun app_canNavigateToAllScreens() {
////        // Check app launches at HOME
////        composeTestRule.onNodeWithText("home").assertIsDisplayed()
////        composeTestRule.onNodeWithText("Hello, Welcome back!").assertIsDisplayed()
////
////        // Navigate to Plan
////        composeTestRule.onNodeWithText("plan").performClick().assertIsDisplayed()
////        composeTestRule.onNodeWithText("Planner").assertIsDisplayed()
////
////        // Navigate to Workout
////        composeTestRule.onNodeWithText("workout").performClick().assertIsDisplayed()
////        composeTestRule.onNodeWithText("MY WEEK OF WORKOUTS").assertIsDisplayed()
////
////        // Navigate to Log
////        composeTestRule.onNodeWithText("log").performClick().assertIsDisplayed()
////        composeTestRule.onNodeWithText("Log").assertIsDisplayed()
////    }
////}
