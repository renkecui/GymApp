package com.example.gymapp.Components

import androidx.compose.runtime.Composable
import com.example.gymapp.data.ExerciseDbItem
import com.example.gymapp.testing.ExampleData
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.ImageLoader
import coil.request.ImageRequest

@Composable
fun DetailedExerciseCard(exercise: ExerciseDbItem) {

    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components { add(GifDecoder.Factory()) }
        .build()

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Exercise Image
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(exercise.gifUrl)
                    .crossfade(true)
                    .build(),
                imageLoader = imageLoader,
                contentDescription = exercise.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Title & Muscle Info
            Text(
                text = exercise.name.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text("Target: ${exercise.target}", fontWeight = FontWeight.SemiBold)
            Text("Equipment: ${exercise.equipment}")
            if (exercise.secondaryMuscles.isNotEmpty()) {
                Text("Secondary: ${exercise.secondaryMuscles.joinToString(", ")}")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Instructions
            Text("Instructions:", fontWeight = FontWeight.SemiBold)
            exercise.instructions.forEachIndexed { index, instruction ->
                Text(text = "${index + 1}. $instruction", fontSize = 13.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailedExerciseCard() {
    val example = com.example.gymapp.testing.ExampleData.backExercise

    DetailedExerciseCard(example)
}
