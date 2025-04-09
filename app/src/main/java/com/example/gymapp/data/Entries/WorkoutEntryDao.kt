package com.example.gymapp.data.Entries

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import java.time.LocalDate

@Dao
interface WorkoutEntryDao {

    @Query("SELECT * FROM WorkoutEntry WHERE date = :date")
    suspend fun getEntriesForDate(date: LocalDate): List<WorkoutEntry>

    @Insert
    suspend fun insertEntry(entry: WorkoutEntry)

    @Delete
    suspend fun deleteEntry(entry: WorkoutEntry)
}
