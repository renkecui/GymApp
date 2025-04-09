package com.example.gymapp.data

// ExerciseDB
data class ExerciseDbItem(
    val bodyPart: String,
    val equipment: String,
    val gifUrl: String,
    val id: String,
    val name: String,
    val target: String,
    val secondaryMuscles: List<String>,
    val instructions: List<String>
)

// bodyPart:""
// equipment:""
// gifUrl:""
// id:""
// name:""
// target:""
// secondaryMuscles: []
// instructions: []
