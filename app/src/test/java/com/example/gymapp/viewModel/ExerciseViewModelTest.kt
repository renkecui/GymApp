package com.example.gymapp.viewModel

import com.example.gymapp.ExerciseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.flow.first
import org.junit.Before
import org.junit.After
import org.junit.Test
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import java.time.DayOfWeek
import com.example.gymapp.data.ExerciseDbItem
import com.example.gymapp.testing.FakeExerciseViewModel
import com.example.gymapp.testing.ExampleData
import com.example.gymapp.testing.FakeWorkoutLogDao

git
@OptIn(ExperimentalCoroutinesApi::class)
class ExerciseViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: ExerciseViewModel

    private val sampleExercise = ExerciseDbItem(
        id = "1",
        name = "Push Ups",
        bodyPart = "chest",
        equipment = "body weight",
        gifUrl = "url",
        target = "chest",
        secondaryMuscles = listOf("triceps", "shoulders"),
        instructions = listOf("Start in plank", "Lower", "Push up")
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ExerciseViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `moveByWeek moves date forward by 1 week`() {
        val today = LocalDate.of(2025, 4, 15)
        viewModel.setDayDate(today)
        viewModel.moveByWeek(forward = true)
        assertEquals(today.plusWeeks(1), viewModel.dayDate.value)
    }

    @Test
    fun `saveSelectedExercise adds to selectedExercises`() = runTest {
        val today = LocalDate.of(2025, 4, 15)
        viewModel.setDayDate(today)

        viewModel.saveSelectedExercise(sampleExercise, today)
        advanceUntilIdle()

        val selected = viewModel.selectedExercises.value
        assertEquals(1, selected.size)
        assertEquals("Push Ups", selected[0].name)
    }

    @Test
    fun `removeSelectedExercise removes from selectedExercises`() = runTest {
        val today = LocalDate.of(2025, 4, 15)
        viewModel.setDayDate(today)

        viewModel.saveSelectedExercise(sampleExercise, today)
        advanceUntilIdle()
        viewModel.removeSelectedExercise(sampleExercise, today)
        advanceUntilIdle()

        val selected = viewModel.selectedExercises.value
        assertTrue(selected.isEmpty())
    }
    @Test
    fun `saveWorkoutLog inserts new log and updates state`() = runTest {
        val fakeDao = FakeWorkoutLogDao()

        val viewModel = object : ExerciseViewModel() {
            init {
                workoutLogDao = fakeDao  // ✅ THIS IS THE LINE
                setDayDate(LocalDate.of(2025, 4, 15))
            }

            override fun loadWorkoutLogsForDate(date: LocalDate) {
                // ✅ Prevent it from running actual coroutine-based DB loading in test
            }
        }

        viewModel.saveWorkoutLog("1", reps = 10, weight = 50f)
        advanceUntilIdle()

        val logs = fakeDao.getLogsForDate(LocalDate.of(2025, 4, 15)).first()
        assertEquals(1, logs.size)
        assertEquals("1", logs[0].exerciseId)
        assertEquals(10, logs[0].reps)
        assertEquals(50f, logs[0].weight)
    }

}
