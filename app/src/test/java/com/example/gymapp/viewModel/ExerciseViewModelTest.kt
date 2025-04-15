//package com.example.gymapp.viewModel
//
//import FakeWorkoutNotesDao
//import android.util.Log
//import com.example.gymapp.ExerciseViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.UnconfinedTestDispatcher
//import kotlinx.coroutines.test.advanceUntilIdle
//import kotlinx.coroutines.test.setMain
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.flow.first
//import org.junit.Before
//import org.junit.After
//import org.junit.Test
//import java.time.LocalDate
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertTrue
//import java.time.DayOfWeek
//import com.example.gymapp.data.ExerciseDbItem
//import com.example.gymapp.data.WorkoutLog
//import com.example.gymapp.data.WorkoutNotes
//import com.example.gymapp.testing.FakeExerciseViewModel
//import com.example.gymapp.testing.ExampleData
//import com.example.gymapp.testing.FakeWorkoutLogDao
//import io.mockk.every
//import io.mockk.mockkStatic
//import io.mockk.unmockkAll
//
//
//@OptIn(ExperimentalCoroutinesApi::class)
//class ExerciseViewModelTest {
//
//    private val testDispatcher = UnconfinedTestDispatcher()
//    private lateinit var viewModel: ExerciseViewModel
//    private lateinit var fakeDao: FakeWorkoutLogDao
//    private lateinit var fakeNotesDao: FakeWorkoutNotesDao
//
//    private val sampleExercise = ExerciseDbItem(
//        id = "1",
//        name = "Push Ups",
//        bodyPart = "chest",
//        equipment = "body weight",
//        gifUrl = "url",
//        target = "chest",
//        secondaryMuscles = listOf("triceps", "shoulders"),
//        instructions = listOf("Start in plank", "Lower", "Push up")
//    )
//
//    private val sampleExercise2 = ExerciseDbItem(
//        id = "2",
//        name = "Squats",
//        bodyPart = "legs",
//        equipment = "body weight",
//        gifUrl = "url",
//        target = "legs",
//        secondaryMuscles = listOf("glutes", "hamstrings"),
//        instructions = listOf("Stand", "Lower", "Stand up")
//    )
//
//    @Before
//    fun setup() {
//        Dispatchers.setMain(testDispatcher)
//        fakeDao = FakeWorkoutLogDao()
//        fakeNotesDao = FakeWorkoutNotesDao()
//        viewModel = createTestViewModel()
//        mockkStatic(Log::class)
//        every { Log.e(any(), any()) } returns 0
//        every { Log.e(any(), any(), any()) } returns 0
//        every { Log.d(any(), any()) } returns 0
//    }
//
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//        unmockkAll()
//    }
//
//    private fun createTestViewModel(): ExerciseViewModel {
//        return object : ExerciseViewModel() {
//            init {
//                workoutLogDao = fakeDao
//                workoutNotesDao = fakeNotesDao
//                setDayDate(LocalDate.of(2025, 4, 15))
//            }
//        }
//    }
//
//    @Test
//    fun `moveByWeek moves date forward by 1 week`() {
//        val today = LocalDate.of(2025, 4, 15)
//        viewModel.setDayDate(today)
//        viewModel.moveByWeek(forward = true)
//        assertEquals(today.plusWeeks(1), viewModel.dayDate.value)
//    }
//
//    @Test
//    fun `moveByWeek moves date backward by 1 week`() {
//        val today = LocalDate.of(2025, 4, 15)
//        viewModel.setDayDate(today)
//        viewModel.moveByWeek(forward = false)
//        assertEquals(today.minusWeeks(1), viewModel.dayDate.value)
//    }
//
//    @Test
//    fun `selectDayOfWeek sets date to specified day in current week`() {
//        val monday = LocalDate.of(2025, 4, 14) // Monday
//        viewModel.setDayDate(monday)
//
//        viewModel.selectDayOfWeek(DayOfWeek.WEDNESDAY)
//        assertEquals(LocalDate.of(2025, 4, 16), viewModel.dayDate.value)
//    }
//
//    @Test
//    fun `saveSelectedExercise adds to selectedExercises`() = runTest {
//        val today = LocalDate.of(2025, 4, 15)
//        viewModel.setDayDate(today)
//
//        viewModel.saveSelectedExercise(sampleExercise, today)
//        advanceUntilIdle()
//
//        val selected = viewModel.selectedExercises.value
//        assertEquals(1, selected.size)
//        assertEquals("Push Ups", selected[0].name)
//    }
//
//    @Test
//    fun `saveSelectedExercise does not add duplicate exercise`() = runTest {
//        val today = LocalDate.of(2025, 4, 15)
//        viewModel.setDayDate(today)
//
//        viewModel.saveSelectedExercise(sampleExercise, today)
//        viewModel.saveSelectedExercise(sampleExercise, today)
//        advanceUntilIdle()
//
//        val selected = viewModel.selectedExercises.value
//        assertEquals(1, selected.size)
//    }
//
//    @Test
//    fun `removeSelectedExercise removes from selectedExercises`() = runTest {
//        val today = LocalDate.of(2025, 4, 15)
//        viewModel.setDayDate(today)
//
//        viewModel.saveSelectedExercise(sampleExercise, today)
//        advanceUntilIdle()
//        viewModel.removeSelectedExercise(sampleExercise, today)
//        advanceUntilIdle()
//
//        val selected = viewModel.selectedExercises.value
//        assertTrue(selected.isEmpty())
//    }
//
//    @Test
//    fun `removeSelectedExercise updates categories correctly`() = runTest {
//        val today = LocalDate.of(2025, 4, 15)
//        viewModel.setDayDate(today)
//
//        viewModel.saveSelectedExercise(sampleExercise, today) // chest
//        viewModel.saveSelectedExercise(sampleExercise2, today) // legs
//        advanceUntilIdle()
//
//        assertEquals(2, viewModel.selectedCategories.value.size)
//
//        viewModel.removeSelectedExercise(sampleExercise, today)
//        advanceUntilIdle()
//
//        assertEquals(1, viewModel.selectedCategories.value.size)
//        assertTrue(viewModel.selectedCategories.value.contains("legs"))
//    }
//
//    @Test
//    fun `saveWorkoutLog inserts new log and updates state`() = runTest {
//        viewModel.saveWorkoutLog("1", reps = 10, weight = 50f)
//        advanceUntilIdle()
//
//        val logs = fakeDao.getLogsForDate(LocalDate.of(2025, 4, 15)).first()
//        assertEquals(1, logs.size)
//        assertEquals("1", logs[0].exerciseId)
//        assertEquals(10, logs[0].reps)
//        assertEquals(50f, logs[0].weight)
//    }
//
//    @Test
//    fun `saveWorkoutLog updates existing log`() = runTest {
//        viewModel.saveWorkoutLog("1", reps = 10, weight = 50f)
//        advanceUntilIdle()
//        viewModel.saveWorkoutLog("1", reps = 12, weight = 55f)
//        advanceUntilIdle()
//
//        val logs = fakeDao.getLogsForDate(LocalDate.of(2025, 4, 15)).first()
//        assertEquals(1, logs.size)
//        assertEquals(12, logs[0].reps)
//        assertEquals(55f, logs[0].weight)
//    }
//
//    @Test
//    fun `loadWorkoutLogsForDate updates state with logs`() = runTest {
//        // Insert a log with a Long ID (assuming your WorkoutLog uses Long for ID)
//        fakeDao.insertLog(WorkoutLog(
//            id = 1L, // Use Long instead of String
//            exerciseId = "ex1", // This is the String ID of the exercise
//            date = LocalDate.of(2025, 4, 15),
//            reps = 10,
//            weight = 50f
//        ))
//
//        viewModel.loadWorkoutLogsForDate(LocalDate.of(2025, 4, 15))
//        advanceUntilIdle()
//
//        assertEquals(1, viewModel.workoutLogs.value.size)
//        assertEquals(10, viewModel.workoutLogs.value["ex1"]?.reps) // Check by exercise ID
//    }
//
//    @Test
//    fun `saveWorkoutNotes inserts notes and updates state`() = runTest {
//        viewModel.saveWorkoutNotes("Test notes")
//        advanceUntilIdle()
//
//        val notes = fakeNotesDao.getNotesForDate(LocalDate.of(2025, 4, 15))
//        assertEquals("Test notes", notes?.notes)
//        assertEquals("Test notes", viewModel.workoutNotes.value?.notes)
//    }
//
//    @Test
//    fun `loadWorkoutNotesForDate updates state with notes`() = runTest {
//        fakeNotesDao.insertNotes(WorkoutNotes(LocalDate.of(2025, 4, 15), "Existing notes"))
//
//        viewModel.loadWorkoutNotesForDate(LocalDate.of(2025, 4, 15))
//        advanceUntilIdle()
//
//        assertEquals("Existing notes", viewModel.workoutNotes.value?.notes)
//    }
//
//    @Test
//    fun `getDayWorkoutList updates selected exercises and categories`() = runTest {
//        val today = LocalDate.of(2025, 4, 15)
//        viewModel.setDayDate(today)
//        viewModel.saveSelectedExercise(sampleExercise, today)
//        advanceUntilIdle()
//
//        // Simulate loading a new day
//        viewModel.setDayDate(today.plusDays(1))
//        advanceUntilIdle()
//
//        // Go back to original day
//        viewModel.setDayDate(today)
//        viewModel.getDayWorkoutList()
//        advanceUntilIdle()
//
//        assertEquals(1, viewModel.selectedExercises.value.size)
//        assertEquals(setOf("chest"), viewModel.selectedCategories.value)
//    }
//
//    @Test
//    fun `calculateStreak updates streak count correctly`() = runTest {
//        val today = LocalDate.of(2025, 4, 15)
//        viewModel.setDayDate(today)
//
//        // Add exercises for today and previous 2 days
//        viewModel.saveSelectedExercise(sampleExercise, today)
//        viewModel.saveSelectedExercise(sampleExercise, today.minusDays(1))
//        viewModel.saveSelectedExercise(sampleExercise, today.minusDays(2))
//        advanceUntilIdle()
//
//        viewModel.calculateStreak()
//        advanceUntilIdle()
//
//        assertEquals(3, viewModel.streak.value)
//    }
//
//    @Test
//    fun `calculateStreak resets when day is missing`() = runTest {
//        val today = LocalDate.of(2025, 4, 15)
//        viewModel.setDayDate(today)
//
//        // Add exercises for today and 2 days ago (missing yesterday)
//        viewModel.saveSelectedExercise(sampleExercise, today)
//        viewModel.saveSelectedExercise(sampleExercise, today.minusDays(2))
//        advanceUntilIdle()
//
//        viewModel.calculateStreak()
//        advanceUntilIdle()
//
//        assertEquals(1, viewModel.streak.value)
//    }
//}