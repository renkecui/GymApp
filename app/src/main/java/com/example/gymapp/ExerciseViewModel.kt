package com.example.gymapp

import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.data.*
import com.example.gymapp.data.ExerciseDBAPI.RetrofitInstance
import com.example.gymapp.data.ExerciseDbItem
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.DayOfWeek
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

open class ExerciseViewModel : ViewModel() {
    private val apiKey = ApiKey.apiKey
    private lateinit var database: AppDatabase
    internal lateinit var workoutLogDao: WorkoutLogDao
    internal lateinit var workoutNotesDao: WorkoutNotesDao

    protected val _exercises = MutableStateFlow<List<ExerciseDbItem>>(emptyList())
    val exercises: StateFlow<List<ExerciseDbItem>> = _exercises

    protected val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    protected val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    protected val _categories = MutableStateFlow<List<String?>>(emptyList())
    val categories: StateFlow<List<String?>> = _categories

    protected val _bodyPartExercises = MutableStateFlow<List<ExerciseDbItem>>(emptyList())
    val bodyPartExercises: StateFlow<List<ExerciseDbItem>> = _bodyPartExercises

    protected val _selectedExercises = MutableStateFlow<List<ExerciseDbItem>>(emptyList())
    val selectedExercises: StateFlow<List<ExerciseDbItem>> = _selectedExercises

    protected val _selectedCategories = MutableStateFlow<Set<String>>(emptySet())
    val selectedCategories: StateFlow<Set<String>> = _selectedCategories

    protected val _workoutLogs = MutableStateFlow<Map<String, WorkoutLog>>(emptyMap())
    val workoutLogs: StateFlow<Map<String, WorkoutLog>> = _workoutLogs

    protected val _workoutNotes = MutableStateFlow<WorkoutNotes?>(null)
    val workoutNotes: StateFlow<WorkoutNotes?> = _workoutNotes

    protected val _streak = MutableStateFlow(0)
    val streak: StateFlow<Int> = _streak

    // Track Today's Date
    @RequiresApi(Build.VERSION_CODES.O)
    internal val _currentDay = MutableStateFlow(LocalDate.now())
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDay: StateFlow<LocalDate> = _currentDay

    // Track the day being viewed
    @RequiresApi(Build.VERSION_CODES.O)
    internal val _dayDate = MutableStateFlow(LocalDate.now())
    @RequiresApi(Build.VERSION_CODES.O)
    val dayDate: StateFlow<LocalDate> = _dayDate

    // Track exercises for specific dates
    val _exercisesByDate = mutableMapOf<LocalDate, MutableList<ExerciseDbItem>>()

    private val api = RetrofitInstance.api

    fun initialize(context: Context) {
        database = AppDatabase.getDatabase(context)
        workoutLogDao = database.workoutLogDao()
        workoutNotesDao = database.workoutNotesDao()
    }


    fun getBodyPartCategory() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = api.getBodyPartCategory(apiKey = apiKey)
                _categories.value = response
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getBodyPartExercises(bodyPart: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d("ExerciseViewModel", "getting list of exercises from api")
                val response = api.getExercisesByBodyPart(
                    apiKey = apiKey,
                    bodyPart = bodyPart
                )
                Log.d("ExerciseViewModel", "response: $response")
                _bodyPartExercises.value = response
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun setDayDate(date: LocalDate) {
        _dayDate.value = date
        getDayWorkoutList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun moveByWeek(forward: Boolean) {
        val currentDate = _dayDate.value
        val newDate = if (forward) {
            currentDate.plusWeeks(1)
        } else {
            currentDate.minusWeeks(1)
        }
        setDayDate(newDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun selectDayOfWeek(dayOfWeek: DayOfWeek) {
        val currentDate = _dayDate.value
        
        // Get the start of the current week (Monday)
        val startOfWeek = currentDate.minusDays((currentDate.dayOfWeek.value - 1).toLong())
        
        // Calculate the day of the week (1-7) for the selected day
        val selectedDayValue = dayOfWeek.value
        
        // Calculate the new date by adding the selected day value - 1 to the start of the week
        // This ensures we stay within the current week
        val newDate = startOfWeek.plusDays((selectedDayValue - 1).toLong())
        
        setDayDate(newDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun saveSelectedExercise(exercise: ExerciseDbItem, date: LocalDate) {
        viewModelScope.launch {
            try {
                // In a real implementation, this would save to the database
                Log.d("Database", "Saving exercise: ${exercise.name} for date: $date")
                
                // Get or create the list of exercises for this date
                val exercisesForDate = _exercisesByDate.getOrPut(date) { mutableListOf() }
                
                // Add the exercise if it's not already in the list
                if (!exercisesForDate.any { it.id == exercise.id }) {
                    exercisesForDate.add(exercise)
                    _exercisesByDate[date] = exercisesForDate
                    
                    // Clear any existing workout logs for this exercise on this date
                    withContext(Dispatchers.IO) {
                        workoutLogDao.deleteLog(date, exercise.id)
                    }
                    
                    // Update the UI state for the current day
                    if (date == _dayDate.value) {
                        _selectedExercises.value = exercisesForDate.toList()
                        // Update selected categories
                        val updatedCategories = _selectedCategories.value.toMutableSet()
                        updatedCategories.add(exercise.bodyPart)
                        _selectedCategories.value = updatedCategories
                        // Reload workout logs to reflect the cleared logs
                        loadWorkoutLogsForDate(date)
                    }
                }
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("Database", "Error saving exercise: ${e.message}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun removeSelectedExercise(exercise: ExerciseDbItem, date: LocalDate) {
        viewModelScope.launch {
            try {
                // In a real implementation, this would remove from the database
                Log.d("Database", "Removing exercise: ${exercise.name} for date: $date")
                
                // Get the list of exercises for this date
                val exercisesForDate = _exercisesByDate[date]
                
                // Remove the exercise if it's in the list
                if (exercisesForDate != null) {
                    exercisesForDate.removeAll { it.id == exercise.id }
                    
                    // Update the UI state for the current day
                    if (date == _dayDate.value) {
                        _selectedExercises.value = exercisesForDate.toList()
                        // Update selected categories
                        val updatedCategories = _selectedCategories.value.toMutableSet()
                        // Only remove the category if no other exercises from that category are selected
                        if (!exercisesForDate.any { it.bodyPart == exercise.bodyPart }) {
                            updatedCategories.remove(exercise.bodyPart)
                        }
                        _selectedCategories.value = updatedCategories
                    }
                }
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("Database", "Error removing exercise: ${e.message}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun calculateStreak() {
        viewModelScope.launch {
            try {
                val today = LocalDate.now()
                var currentStreak = 0
                var currentDate = today

                while (true) {
                    // Check if there are any planned exercises for this date
                    val hasPlannedExercises = _exercisesByDate[currentDate]?.isNotEmpty() ?: false

                    if (hasPlannedExercises) {
                        currentStreak++
                        currentDate = currentDate.minusDays(1)
                    } else {
                        break
                    }
                }

                _streak.value = currentStreak
                Log.d("ExerciseViewModel", "Current streak: $currentStreak")
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("ExerciseViewModel", "Error calculating streak: ${e.message}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun saveWorkoutLog(exerciseId: String, reps: Int, weight: Float) {
        viewModelScope.launch {
            try {
                val date = _dayDate.value
                Log.d("Database", "Saving workout log - Exercise: $exerciseId, Date: $date, Reps: $reps, Weight: $weight")
                
                val existingLog = workoutLogDao.getLogForExercise(date, exerciseId)
                
                if (existingLog != null) {
                    // Update existing log
                    val updatedLog = existingLog.copy(reps = reps, weight = weight)
                    workoutLogDao.updateLog(updatedLog)
                    Log.d("Database", "Updated existing log: $updatedLog")
                } else {
                    // Create new log
                    val newLog = WorkoutLog(
                        exerciseId = exerciseId,
                        date = date,
                        reps = reps,
                        weight = weight
                    )
                    workoutLogDao.insertLog(newLog)
                    Log.d("Database", "Created new log: $newLog")
                }
                
                // Update the UI state
                loadWorkoutLogsForDate(date)
                calculateStreak() // Update streak after saving log
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("Database", "Error saving workout log: ${e.message}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun loadWorkoutLogsForDate(date: LocalDate) {
        viewModelScope.launch {
            try {
                Log.d("Database", "Loading workout logs for date: $date")
                val logs = workoutLogDao.getLogsForDate(date)
                logs.collect { logList ->
                    val logMap = logList.associateBy { it.exerciseId }
                    _workoutLogs.value = logMap
                    Log.d("Database", "Loaded ${logList.size} workout logs: $logList")
                }
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("Database", "Error loading workout logs: ${e.message}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun loadWorkoutLogs() {
        loadWorkoutLogsForDate(_dayDate.value)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun saveWorkoutNotes(notes: String) {
        viewModelScope.launch {
            try {
                Log.d("ExerciseViewModel", "Saving notes for date: ${dayDate.value}, notes: $notes")
                val currentNotes = WorkoutNotes(dayDate.value, notes)
                withContext(Dispatchers.IO) {
                    workoutNotesDao.insertNotes(currentNotes)
                }
                _workoutNotes.value = currentNotes
                calculateStreak() // Update streak after saving notes
                Log.d("ExerciseViewModel", "Notes saved and state updated")
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("Database", "Error saving workout notes: ${e.message}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun loadWorkoutNotesForDate(date: LocalDate) {
        viewModelScope.launch {
            try {
                Log.d("ExerciseViewModel", "Loading notes for date: $date")
                val notes = withContext(Dispatchers.IO) {
                    workoutNotesDao.getNotesForDate(date)
                }
                Log.d("ExerciseViewModel", "Loaded notes: ${notes?.notes ?: "null"}")
                _workoutNotes.value = notes
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("Database", "Error loading workout notes: ${e.message}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun loadWorkoutNotes() {
        viewModelScope.launch {
            try {
                Log.d("ExerciseViewModel", "Loading notes for current date: ${dayDate.value}")
                val notes = withContext(Dispatchers.IO) {
                    workoutNotesDao.getNotesForDate(dayDate.value)
                }
                Log.d("ExerciseViewModel", "Loaded notes: ${notes?.notes ?: "null"}")
                _workoutNotes.value = notes
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("Database", "Error loading workout notes: ${e.message}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun getDayWorkoutList() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Get the exercises for the current day
                val exercisesForDate = _exercisesByDate[_dayDate.value] ?: emptyList()
                
                // Update the UI state
                _selectedExercises.value = exercisesForDate
                
                // Update selected categories
                val categoriesWithExercises = exercisesForDate.map { it.bodyPart }.toSet()
                _selectedCategories.value = categoriesWithExercises
                
                // Load workout logs and notes for the current day
                loadWorkoutLogsForDate(_dayDate.value)
                loadWorkoutNotesForDate(_dayDate.value)
                calculateStreak() // Update streak when loading the workout list
                
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("Database", "Error getting day workout list: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
