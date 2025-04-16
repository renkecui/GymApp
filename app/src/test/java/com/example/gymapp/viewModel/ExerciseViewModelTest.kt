package com.example.gymapp.viewModel

import com.example.gymapp.ExerciseViewModel
import com.example.gymapp.data.ExerciseDbItem
import com.example.gymapp.data.WorkoutNotes
import com.example.gymapp.testing.ExampleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.After
import org.junit.Test
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull

import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.time.DayOfWeek


// ExerciseViewModelTest.kt
@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ExerciseViewModelTest {
    private lateinit var viewModel: ExerciseViewModel
    private lateinit var fakeLogDao: FakeWorkoutLogDao
    private lateinit var fakeNotesDao: FakeWorkoutNotesDao
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeLogDao = FakeWorkoutLogDao()
        fakeNotesDao = FakeWorkoutNotesDao()
        viewModel = ExerciseViewModel().apply {
            workoutLogDao = fakeLogDao
            workoutNotesDao = fakeNotesDao
            setDayDate(LocalDate.of(2025, 4, 15))
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        fakeLogDao.clear()
        fakeNotesDao.clear()
    }

    @Test
    fun testCalculateStreak() = runBlocking {
        // Arrange: simulate exercises planned on three consecutive days.
        val today = viewModel.currentDay.value
        viewModel._exercisesByDate[today] = mutableListOf(ExampleData.backExercise)
        viewModel._exercisesByDate[today.minusDays(1)] =
            mutableListOf(ExampleData.backExercise)
        viewModel._exercisesByDate[today.minusDays(2)] =
            mutableListOf(ExampleData.legExerciseDbItem)
        // No exercise for today.minusDays(3) so that streak stops there.

        // Act
        viewModel.calculateStreak()
        delay(100) // wait for coroutine

        // Assert: streak should count three consecutive days.
        assertEquals(3, viewModel.streak.value)
    }

    @Test
    fun testSaveSelectedExercise() = runBlocking {
        // Arrange: create a dummy exercise and use the current test date.
        val date = viewModel.dayDate.value
        val exercise = ExampleData.legExerciseDbItem

        // Act: save the exercise for that date.
        viewModel.saveSelectedExercise(exercise, date)
        delay(100) // allow coroutine to complete

        // Assert: the selected exercise appears in UI state and its category is updated.
        val selected = viewModel.selectedExercises.value
        assertTrue(selected.any { it.id == exercise.id })
        assertTrue(viewModel.selectedCategories.value.contains("legs"))
    }

    @Test
    fun testRemoveSelectedExercise() = runBlocking {
        // Arrange: add an exercise first.
        val date = viewModel.dayDate.value
        val exercise = ExampleData.legExerciseDbItem
        viewModel.saveSelectedExercise(exercise, date)
        delay(100)

        // Act: remove the exercise.
        viewModel.removeSelectedExercise(exercise, date)
        delay(100)

        // Assert: the exercise should be removed and its category should be cleared when no other exercise exists.
        val selected = viewModel.selectedExercises.value
        assertFalse(selected.any { it.id == exercise.id })
        assertFalse(viewModel.selectedCategories.value.contains("legs"))
    }

    @Test
    fun testSaveWorkoutLogAndLoadWorkoutLogsForDate() = runBlocking {
        // Arrange
        val date = viewModel.dayDate.value
        val exerciseId = "1"

        // Act: save a workout log.
        viewModel.saveWorkoutLog(exerciseId, reps = 10, weight = 100.0f)
        delay(100)
        // Then load logs.
        viewModel.loadWorkoutLogsForDate(date)
        delay(100)

        // Assert: check that the log exists in the viewmodel state.
        val logs = viewModel.workoutLogs.value
        val log = logs[exerciseId]
        assertNotNull(log)
        assertEquals(10, log?.reps)
        assertEquals(100.0f, log?.weight)
    }

    @Test
    fun testSaveAndLoadWorkoutNotes() = runBlocking {
        // Arrange
        val date = viewModel.dayDate.value
        val notesText = "Great workout today!"

        // Act: save workout notes.
        viewModel.saveWorkoutNotes(notesText)
        delay(100)
        // Clear the current notes state and reload to simulate a fresh load.
        viewModel._workoutNotes.value = null
        viewModel.loadWorkoutNotesForDate(date)
        delay(100)

        // Assert: verify that the notes were saved and reloaded.
        val notes = viewModel.workoutNotes.value
        assertNotNull(notes)
        assertEquals(notesText, notes?.notes)
    }

    @Test
    fun testMoveByWeek() = runBlocking {
        // Arrange: record the initial date.
        val initialDate = viewModel.dayDate.value

        // Act: move forward by one week.
        viewModel.moveByWeek(forward = true)
        delay(100)
        // Assert: the day should be advanced by 7 days.
        assertEquals(initialDate.plusWeeks(1), viewModel.dayDate.value)

        // Act: move backward by one week.
        viewModel.moveByWeek(forward = false)
        delay(100)
        // Assert: the date is back to the original.
        assertEquals(initialDate, viewModel.dayDate.value)
    }

    @Test
    fun testSelectDayOfWeek() = runBlocking {
        // Arrange: assume current test date is within a week.
        val initialDate = viewModel.dayDate.value

        viewModel.selectDayOfWeek(DayOfWeek.MONDAY)
        delay(100)

        // Calculate the Monday for the week of the initial date.
        val monday = initialDate.minusDays((initialDate.dayOfWeek.value - 1).toLong())
        // Assert: the dayDate should be updated to Monday.
        assertEquals(monday, viewModel.dayDate.value)
    }

    @Test
    fun testGetDayWorkoutList() = runBlocking {
        // Arrange: create a dummy exercise for the current date.
        val date = viewModel.dayDate.value
        val exercise = ExampleData.legExerciseDbItem
        viewModel._exercisesByDate[date] = mutableListOf(exercise)

        // Act
        viewModel.getDayWorkoutList()
        delay(100)

        // Assert: check that selected exercises and categories are updated.
        val selected = viewModel.selectedExercises.value
        assertTrue(selected.any { it.id == exercise.id })
        assertTrue(viewModel.selectedCategories.value.contains("legs"))
    }

    @Test
    fun `saveWorkoutLog inserts new log`() = runTest {
        viewModel.saveWorkoutLog("ex1", 10, 50f)
        advanceUntilIdle()

        val logs = fakeLogDao.getLogsForDate(LocalDate.of(2025, 4, 15)).first()
        assertEquals(1, logs.size)
        assertEquals(10, logs[0].reps)
    }

    @Test
    fun `loadWorkoutNotes returns saved notes`() = runTest {
        fakeNotesDao.insertNotes(WorkoutNotes(LocalDate.of(2025, 4, 15), "Test notes"))

        viewModel.loadWorkoutNotesForDate(LocalDate.of(2025, 4, 15))
        advanceUntilIdle()

        assertEquals("Test notes", viewModel.workoutNotes.value?.notes)
    }

    @Test
    fun `error saving log is handled gracefully`() = runTest {
        // Arrange: make the fake DAO throw an exception.
        fakeLogDao.throwError = true

        // Act
        viewModel.saveWorkoutLog("ex1", 10, 50f)
        advanceUntilIdle()

        // Assert: verify that the viewModel's error state contains "Test error"
        val errorMsg = viewModel.error.value
        assertNotNull("Error message should not be null", errorMsg)
        assertTrue("Error message should mention 'Test error'", errorMsg!!.contains("Test error"))
    }
}