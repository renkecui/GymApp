package com.example.gymapp.testing

import com.example.gymapp.ExerciseViewModel

class FakeExerciseViewModel : ExerciseViewModel() {
    init {
        _exercises.value = ExampleData.exerciseList
    }
}
