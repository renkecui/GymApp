package com.example.gymapp.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

 class NewStreakKtTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun newStreak_displaysCorrectTexts() {
   // Set the content with a specific streak count to test
   val streakCount = 7
   composeTestRule.setContent {
    // Optionally wrap in a container (if needed for layout)
    NewStreak(streakCount = streakCount)
   }

   // Assert that the primary text is displayed correctly.
   composeTestRule.onNodeWithText("Streak: $streakCount days")
    .assertIsDisplayed()

   // Assert that the encouragement text is displayed.
   composeTestRule.onNodeWithText("Keep it Up!")
    .assertIsDisplayed()
  }

  @Test
  fun newStreak_hasStarIcon() {
   // Set the content of the composable
   composeTestRule.setContent {
    NewStreak(streakCount = 10)
   }

   // Verify that the Icon is present by its content description.
   composeTestRule.onNodeWithContentDescription("Star")
    .assertIsDisplayed()
  }
 }
