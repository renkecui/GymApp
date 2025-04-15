//package com.example.gymapp
//
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.compose.ui.test.onNodeWithText
//import androidx.compose.ui.test.performClick
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import java.time.DayOfWeek
//import androidx.compose.runtime.*
//import com.example.gymapp.components.WeekView
//
//@RunWith(AndroidJUnit4::class)
//class WeekViewTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @Test
//    fun clickingADay_updatesTheHighlightedDay() {
//        // Use a state hoisting container for testing.
//        composeTestRule.setContent {
//            // Hold the selected DayOfWeek state
//            var selectedDay by remember { mutableStateOf(DayOfWeek.MONDAY) }
//
//            // Optionally, wrap in a container so the view fills the screen.
//            WeekView(
//                highlightedDay = selectedDay,
//                modifier = Modifier.fillMaxSize(),
//                onDayClick = { selectedDay = it }
//            )
//        }
//
//        // Verify initial state: "Mon" should be highlighted.
//        composeTestRule.onNodeWithText("Mon").assertExists("Expected initial highlighted day to be Monday")
//        // Now simulate a click on "Tue" text to change the highlighted day.
//        composeTestRule.onNodeWithText("Tue").performClick()
//        // Allow recomposition to complete.
//        composeTestRule.waitForIdle()
//        composeTestRule.onNodeWithText("Tue").assertExists("Expected Tuesday to be displayed after click")
//    }
//}
