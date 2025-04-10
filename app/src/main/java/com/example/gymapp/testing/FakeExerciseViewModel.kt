package com.example.gymapp.testing

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.gymapp.ExerciseViewModel
import com.example.gymapp.data.ExerciseDbItem
import java.time.LocalDate
import java.time.DayOfWeek

@RequiresApi(Build.VERSION_CODES.O)
class FakeExerciseViewModel : ExerciseViewModel() {
    
    // Track exercises for specific dates
    private val _fakeExercisesByDate = mutableMapOf<LocalDate, MutableList<ExerciseDbItem>>()
    
    override fun getDayWorkoutList() {
        // Get the exercises for the current day
        val exercisesForDate = _fakeExercisesByDate[_dayDate.value] ?: emptyList()
        
        // Update the UI state
        _selectedExercises.value = exercisesForDate
    }
    
    override fun saveSelectedExercise(exercise: ExerciseDbItem, date: LocalDate) {
        // Get or create the list of exercises for this date
        val exercisesForDate = _fakeExercisesByDate.getOrPut(date) { mutableListOf() }
        
        // Add the exercise if it's not already in the list
        if (!exercisesForDate.any { it.id == exercise.id }) {
            exercisesForDate.add(exercise)
            _fakeExercisesByDate[date] = exercisesForDate
            
            // Update the UI state for the current day
            if (date == _dayDate.value) {
                _selectedExercises.value = exercisesForDate.toList()
            }
        }
    }
    
    override fun removeSelectedExercise(exercise: ExerciseDbItem, date: LocalDate) {
        // Get the list of exercises for this date
        val exercisesForDate = _fakeExercisesByDate[date]
        
        // Remove the exercise if it's in the list
        if (exercisesForDate != null) {
            exercisesForDate.removeAll { it.id == exercise.id }
            
            // Update the UI state for the current day
            if (date == _dayDate.value) {
                _selectedExercises.value = exercisesForDate.toList()
            }
        }
    }
    
    override fun setDayDate(date: LocalDate) {
        _dayDate.value = date
        getDayWorkoutList()
    }
    
    override fun moveByWeek(forward: Boolean) {
        val currentDate = _dayDate.value
        val newDate = if (forward) {
            currentDate.plusWeeks(1)
        } else {
            currentDate.minusWeeks(1)
        }
        setDayDate(newDate)
    }
    
    override fun selectDayOfWeek(dayOfWeek: DayOfWeek) {
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
}
