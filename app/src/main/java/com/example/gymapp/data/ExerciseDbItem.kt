package com.example.gymapp.data

// ExerciseDB
data class ExerciseDbItem(
    open val bodyPart: String,
    val equipment: String,
    val gifUrl: String,
    open val id: String,
    open val name: String,
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
