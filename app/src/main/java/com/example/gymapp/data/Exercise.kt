package com.example.gymapp.data


// TODO this is only an EXAMPLE of an Exercise
data class Exercise(
    val id: Int,
    val name: String,
    val description: String,
    val muscleGroup: String,
    val durationMinutes: Int,
    val difficulty: Difficulty = Difficulty.MEDIUM,
    val imageResId: Int? = null,
    val reps: Int? = null,
    val isCompleted: Boolean = false
)

enum class Difficulty {
    EASY, MEDIUM, HARD
}
