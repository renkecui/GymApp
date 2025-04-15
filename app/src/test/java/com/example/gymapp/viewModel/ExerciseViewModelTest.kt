package com.example.gymapp.viewModel

import com.example.gymapp.ExerciseViewModel
import com.example.gymapp.data.ExerciseDbItem
import com.example.gymapp.data.WorkoutNotes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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

import org.junit.Assert.assertTrue


// ExerciseViewModelTest.kt
@OptIn(ExperimentalCoroutinesApi::class)
class ExerciseViewModelTest {
    private lateinit var viewModel: ExerciseViewModel
    private lateinit var fakeLogDao: FakeWorkoutLogDao
    private lateinit var fakeNotesDao: FakeWorkoutNotesDao
    private val testDispatcher = UnconfinedTestDispatcher()

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

        viewModel.saveWorkoutLog("ex1", 10, 50f)
        advanceUntilIdle()

        // Verify error state or that the app didn't crash
        assertTrue(viewModel.error.value?.contains("Test error") == true)
    }
}