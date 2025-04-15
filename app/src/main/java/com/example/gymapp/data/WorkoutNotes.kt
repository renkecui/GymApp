package com.example.gymapp.data

import androidx.room.*
import java.time.LocalDate

@Entity(tableName = "workout_notes")
data class WorkoutNotes(
    @PrimaryKey
    val date: LocalDate,
    val notes: String
)

@Dao
interface WorkoutNotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: WorkoutNotes)

    @Query("SELECT * FROM workout_notes WHERE date = :date")
    fun getNotesForDate(date: LocalDate): WorkoutNotes?

    @Query("DELETE FROM workout_notes WHERE date = :date")
    suspend fun deleteNotesForDate(date: LocalDate)
} 