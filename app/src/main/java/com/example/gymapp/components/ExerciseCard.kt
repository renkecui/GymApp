package com.example.gymapp.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.draw.clip
import com.example.gymapp.data.ExerciseDbItem

@Composable
fun ExerciseItemCard(
    exercise: ExerciseDbItem,
    isSelected: Boolean,
    onToggleSelect: (ExerciseDbItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) Color(0xFFA5D6A7) else Color.LightGray)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = exercise.name.replaceFirstChar { it.uppercase() },
                fontWeight = FontWeight.SemiBold)
            Text(
                text = exercise.target.replaceFirstChar { it.uppercase() },
                fontSize = 12.sp,
                color = Color.DarkGray
            )
        }

        IconButton(onClick = { onToggleSelect(exercise) }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = if (isSelected) "Remove from Plan" else "Add to Plan"
            )
        }
    }
}




@Preview
@Composable
fun PreviewExerciseCardPushUp() {
    val exampleExercise = com.example.gymapp.testing.ExampleData.backExercise
    val isSelected = false

    ExerciseItemCard(exampleExercise,isSelected, onToggleSelect = {})
}
