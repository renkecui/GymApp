package com.example.gymapp.components

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.ExerciseViewModel


@Composable
fun ExerciseCategories(
    exerciseDbList: List<String?>,
    viewModel: ExerciseViewModel,
    navController: NavHostController
) {
    viewModel.getBodyPartCategory()
    val selectedCategories by viewModel.selectedCategories.collectAsState()

    Spacer(modifier = Modifier.height(8.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text("Categories", style = MaterialTheme.typography.titleLarge)
    }
    Spacer(modifier = Modifier.height(16.dp))
    // Exercise buttons in a 2-column grid
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        items(exerciseDbList) { exerciseBodyPart ->
            val isSelected = exerciseBodyPart != null && selectedCategories.contains(exerciseBodyPart)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (isSelected) Color(0xFFA5D6A7) else Color.LightGray)
                    .clickable {
                        exerciseBodyPart?.let { bodyPart ->
                            val encodedPart = Uri.encode(bodyPart)
                            navController.navigate("plan/$encodedPart")
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                if (exerciseBodyPart != null) {
                    Text(
                        text = exerciseBodyPart.replaceFirstChar { it.uppercase() },
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewExerciseCategories() {
    val testExerciseList = com.example.gymapp.testing.ExampleData.bodyPartList
    val fakeViewModel = remember { com.example.gymapp.testing.FakeExerciseViewModel() }

    ExerciseCategories(testExerciseList, fakeViewModel, navController = rememberNavController())
}
