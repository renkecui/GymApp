//package com.example.gymapp
//
//import androidx.compose.ui.test.assertIsDisplayed
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.compose.ui.test.onNodeWithContentDescription
//import androidx.compose.ui.test.onNodeWithText
//import androidx.compose.ui.test.printToLog
//import androidx.compose.ui.test.performClick
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.ui.unit.dp
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.gymapp.components.*
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//
//// Use the AndroidJUnit4 runner to run instrumented tests
//@RunWith(AndroidJUnit4::class)
//class NewStreakTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @Test
//    fun newStreak_displaysCorrectTexts() {
//        // Set the content with a specific streak count to test
//        val streakCount = 7
//        composeTestRule.setContent {
//            // Optionally wrap in a container (if needed for layout)
//            NewStreak(streakCount = streakCount)
//        }
//
//        // Assert that the primary text is displayed correctly.
//        composeTestRule.onNodeWithText("Streak: $streakCount days")
//            .assertIsDisplayed()
//
//        // Assert that the encouragement text is displayed.
//        composeTestRule.onNodeWithText("Keep it Up!")
//            .assertIsDisplayed()
//    }
//
//    @Test
//    fun newStreak_hasStarIcon() {
//        // Set the content of the composable
//        composeTestRule.setContent {
//            NewStreak(streakCount = 10)
//        }
//
//        // Verify that the Icon is present by its content description.
//        composeTestRule.onNodeWithContentDescription("Star")
//            .assertIsDisplayed()
//    }
//}
