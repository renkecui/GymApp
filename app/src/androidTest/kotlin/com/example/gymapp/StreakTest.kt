//package com.example.gymapp
//
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.ui.test.assertIsDisplayed
//import androidx.compose.ui.test.assertIsNotDisplayed
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.compose.ui.test.onNodeWithText
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.gymapp.Components.Streaks
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import java.time.DayOfWeek
//
//@RunWith(AndroidJUnit4::class)
//class StreakTest {
//
//    @get:Rule
//    val rule = createComposeRule()
//
//    @Before
//    fun setup() {
//        rule.setContent {
//            MaterialTheme {
//                Streaks(highlightedDay = DayOfWeek.MONDAY)
//            }
//        }
//    }
//
//    @Test
//    fun streaks_displayWeekLabel() {
//        // Verify that the "Week 1" text is displayed on the screen.
////        rule.onNodeWithText("Week 1").assertExists()
////        rule.onNodeWithText("Week 1").assertIsDisplayed()
////        rule.onNodeWithText("Error").assertIsNotDisplayed()
//
//    }
//
//}
