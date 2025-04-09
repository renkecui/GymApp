package com.example.gymapp.Components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.gymapp.ExerciseViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import com.example.gymapp.testing.FakeExerciseViewModel
import com.example.gymapp.data.ExerciseDbItem
@Composable
fun BodyPartExerciseList(
    bodyPart: String,
    viewModel: ExerciseViewModel
) {
    var selectedExercises by remember { mutableStateOf(setOf<ExerciseDbItem>()) }
    Log.d("BodyPartExerciseList", "list to get: $bodyPart")
//    val bodyPartExerciseList = viewModel.getBodyPartExercises(bodyPart)
    viewModel.getBodyPartExercises(bodyPart)
    val bodyPartExerciseList by viewModel.bodyPartExercises.collectAsState()

    Log.d("BodyPartExerciseList", "list of exercises: $bodyPartExerciseList")


    Column(modifier = Modifier.padding(16.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = bodyPart.replaceFirstChar { it.uppercase() },
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            items(bodyPartExerciseList) { exercise ->
                ExerciseItemCard(
                    exercise = exercise,
                    isSelected = selectedExercises.contains(exercise),
                    onToggleSelect = { clickedExercise ->
                        selectedExercises = selectedExercises.toMutableSet().apply {
                            if (contains(clickedExercise)) remove(clickedExercise)
                            else add(clickedExercise)
                        }
                    }
                )
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewBodyPartExerciseList() {
    val fakeViewModel = remember { FakeExerciseViewModel() }

    BodyPartExerciseList(
        bodyPart = "Abs",
        viewModel = fakeViewModel
    )
}
