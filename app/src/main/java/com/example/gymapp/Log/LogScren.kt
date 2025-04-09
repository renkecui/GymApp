package com.example.gymapp.Log

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.Components.LogWorkout
import com.example.gymapp.Components.WeekView
import com.example.gymapp.R
import java.time.LocalDate
import java.util.Locale
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import com.example.gymapp.ExerciseViewModel
import com.example.gymapp.testing.FakeExerciseViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogScreen(
    navController: NavHostController,
    viewModel: ExerciseViewModel
) {
    val currentDay by viewModel.currentDay.collectAsState()
    val highlightedDay = currentDay.dayOfWeek
    Column(modifier = Modifier.padding(16.dp)) {

        // Centered Title
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.log_title).uppercase(Locale.ENGLISH),
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            WeekView(highlightedDay = highlightedDay, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        LogWorkout()

    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun LogScreenPreview() {
    // Provide a fake navController for preview
    LogScreen(navController = rememberNavController(), viewModel = FakeExerciseViewModel())
}