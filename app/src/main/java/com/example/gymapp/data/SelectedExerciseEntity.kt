package com.example.gymapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "selected_exercises")
data class SelectedExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val exerciseId: String,
    val name: String,
    val bodyPart: String,
    val target: String,
    val equipment: String,
    val date: LocalDate
) 