package com.example.gymapp

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.data.ApiKey
import com.example.gymapp.data.ExerciseDBAPI.RetrofitInstance
import com.example.gymapp.data.ExerciseDbItem
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.DayOfWeek


open class ExerciseViewModel : ViewModel() {
    private val apiKey = ApiKey.apiKey

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

    // Track Today's Date
    @RequiresApi(Build.VERSION_CODES.O)
    protected val _currentDay = MutableStateFlow(LocalDate.now())
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDay: StateFlow<LocalDate> = _currentDay

    // Track the day being viewed
    @RequiresApi(Build.VERSION_CODES.O)
    protected val _dayDate = MutableStateFlow(LocalDate.now())
    @RequiresApi(Build.VERSION_CODES.O)
    val dayDate: StateFlow<LocalDate> = _dayDate

    // Track exercises for specific dates
    protected val _exercisesByDate = mutableMapOf<LocalDate, MutableList<ExerciseDbItem>>()

    private val api = RetrofitInstance.api // You'll create this in Step 3

    fun fetchExercises() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = api.getAllExercises(apiKey = apiKey)
                _exercises.value = response
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
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
                    
                    // Update the UI state for the current day
                    if (date == _dayDate.value) {
                        _selectedExercises.value = exercisesForDate.toList()
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
                    }
                }
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("Database", "Error removing exercise: ${e.message}")
            }
        }
    }

    open fun getDayWorkoutList() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // In a real implementation, this would fetch from the database
                Log.d("Database", "Getting exercises for date: ${_dayDate.value}")
                
                // Get the exercises for the current day
                val exercisesForDate = _exercisesByDate[_dayDate.value] ?: emptyList()
                
                // Update the UI state
                _selectedExercises.value = exercisesForDate
                
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
