package com.example.gymapp.testing

import com.example.gymapp.data.ExerciseDbItem

object ExampleData {
    val backExercisesList = listOf(
        ExerciseDbItem(
            bodyPart = "back",
            equipment = "cable",
            gifUrl = "https://v2.exercisedb.io/image/Qd1CX-5YJ83fD9",
            id = "0007",
            name = "alternate lateral pulldown",
            target = "lats",
            secondaryMuscles = listOf(
                "biceps",
                "rhomboids"
            ),
            instructions = listOf(
                "Sit on the cable machine with your back straight and feet flat on the ground.",
                "Grasp the handles with an overhand grip, slightly wider than shoulder-width apart.",
                "Lean back slightly and pull the handles towards your chest, squeezing your shoulder blades together.",
                "Pause for a moment at the peak of the movement, then slowly release the handles back to the starting position.",
                "Repeat for the desired number of repetitions."
            )
        ),
        ExerciseDbItem(
            bodyPart = "back",
            equipment = "leverage machine",
            gifUrl = "https://v2.exercisedb.io/image/MLPCySEpAZWUMX",
            id = "0015",
            name = "assisted parallel close grip pull-up",
            target = "lats",
            secondaryMuscles = listOf(
                "biceps",
                "forearms"
            ),
            instructions = listOf(
                "Adjust the machine to your desired weight and height.",
                "Place your hands on the parallel bars with a close grip, palms facing each other.",
                "Hang from the bars with your arms fully extended and your feet off the ground.",
                "Engage your back muscles and pull your body up towards the bars, keeping your elbows close to your body.",
                "Continue pulling until your chin is above the bars.",
                "Pause for a moment at the top, then slowly lower your body back down to the starting position.",
                "Repeat for the desired number of repetitions."
            )
        )
    )

    val bodyPartList = listOf(
    "back",
    "cardio",
    "chest",
    "lower arms",
    "lower legs",
    "neck",
    "shoulders",
    "upper arms",
    "upper legs",
    "waist"
    )

    val backExercise = ExerciseDbItem(
        bodyPart = "back",
        equipment = "cable",
        gifUrl = "https://v2.exercisedb.io/image/Qd1CX-5YJ83fD9",
        id = "0007",
        name = "alternate lateral pulldown",
        target = "lats",
        secondaryMuscles = listOf("biceps", "rhomboids"),
        instructions = listOf(
            "Sit on the cable machine with your back straight and feet flat on the ground.",
            "Grasp the handles with an overhand grip, slightly wider than shoulder-width apart.",
            "Lean back slightly and pull the handles towards your chest, squeezing your shoulder blades together.",
            "Pause for a moment at the peak of the movement, then slowly release the handles back to the starting position.",
            "Repeat for the desired number of repetitions."
        )
    )

    val legExerciseDbItem = ExerciseDbItem(
        bodyPart = "legs",
        equipment = "barbell",
        gifUrl = "https://example.com/gifs/barbell-squat.gif",
        id = "0010",
        name = "Barbell Squat",
        target = "quadriceps",
        secondaryMuscles = listOf("glutes", "hamstrings", "lower back"),
        instructions = listOf(
            "Stand with your feet shoulder-width apart.",
            "Lower your body by bending your knees.",
            "Push through your heels to stand back up."
        )
    )


    val exerciseList = listOf(
        ExerciseDbItem(
            bodyPart = "Abs",
            equipment = "body weight",
            gifUrl = "",
            id = "1",
            name = "Crunch",
            target = "abs",
            secondaryMuscles = listOf("obliques"),
            instructions = listOf("Lie down", "Crunch up", "Return")
        ),
        ExerciseDbItem(
            bodyPart = "Back",
            equipment = "pull-up bar",
            gifUrl = "",
            id = "2",
            name = "Pull-up",
            target = "lats",
            secondaryMuscles = listOf("biceps"),
            instructions = listOf("Grip bar", "Pull up", "Lower down")
        ),
        ExerciseDbItem(
            bodyPart = "Biceps",
            equipment = "dumbbell",
            gifUrl = "",
            id = "3",
            name = "Bicep Curl",
            target = "biceps",
            secondaryMuscles = listOf("forearms"),
            instructions = listOf("Curl up", "Squeeze", "Lower")
        ),
        ExerciseDbItem(
            bodyPart = "legs",
            equipment = "barbell",
            gifUrl = "https://example.com/gifs/barbell-squat.gif",
            id = "0010",
            name = "Barbell Squat",
            target = "quadriceps",
            secondaryMuscles = listOf("glutes", "hamstrings", "lower back"),
            instructions = listOf(
                "Stand with your feet shoulder-width apart.",
                "Lower your body by bending your knees.",
                "Push through your heels to stand back up."
            )
        ),
        ExerciseDbItem(
            bodyPart = "shoulders",
            equipment = "dumbbell",
            gifUrl = "https://example.com/gifs/dumbbell-shoulder-press.gif",
            id = "0021",
            name = "Dumbbell Shoulder Press",
            target = "deltoids",
            secondaryMuscles = listOf("triceps", "trapezius"),
            instructions = listOf(
                "Sit on a bench with back support and hold a dumbbell in each hand at shoulder level.",
                "Press the dumbbells upward until your arms are fully extended.",
                "Pause at the top and slowly lower the weights back to the starting position.",
                "Repeat for the desired number of repetitions."
            )
        )
    )
}