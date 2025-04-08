package com.example.gymapp.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exerciseEntity")
data class ExerciseEntity (
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
    val imageUrl: String
)