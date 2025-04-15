package com.example.gymapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface WorkoutLogDao {
    @Insert
    suspend fun insertLog(log: WorkoutLog)

    @Update
    suspend fun updateLog(log: WorkoutLog)

    @Query("SELECT * FROM workout_logs WHERE date = :date AND exerciseId = :exerciseId")
    suspend fun getLogForExercise(date: LocalDate, exerciseId: String): WorkoutLog?

    @Query("SELECT * FROM workout_logs WHERE date = :date")
    fun getLogsForDate(date: LocalDate): Flow<List<WorkoutLog>>

    @Query("DELETE FROM workout_logs WHERE date = :date AND exerciseId = :exerciseId")
    suspend fun deleteLog(date: LocalDate, exerciseId: String)
} 