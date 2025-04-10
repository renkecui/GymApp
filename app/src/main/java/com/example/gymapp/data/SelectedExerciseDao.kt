package com.example.gymapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface SelectedExerciseDao {
    @Insert
    suspend fun insertExercise(exercise: SelectedExerciseEntity)

    @Delete
    suspend fun deleteExercise(exercise: SelectedExerciseEntity)

    @Query("SELECT * FROM selected_exercises WHERE date = :date")
    fun getExercisesForDate(date: LocalDate): Flow<List<SelectedExerciseEntity>>

    @Query("DELETE FROM selected_exercises WHERE date = :date")
    suspend fun deleteExercisesForDate(date: LocalDate)
} 