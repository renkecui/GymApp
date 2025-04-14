package com.example.gymapp.HomeScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.gymapp.Components.NewStreak
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.Components.Streaks
import com.example.gymapp.Components.TodayWorkoutSummaryCard
import com.example.gymapp.ExerciseViewModel
import com.example.gymapp.R
import com.example.gymapp.testing.FakeExerciseViewModel
import java.time.LocalDate
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ExerciseViewModel
) {
    val streak by viewModel.streak.collectAsState()
    val currentDay by viewModel.currentDay.collectAsState()
    val highlightedDay = currentDay.dayOfWeek

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Centered Title
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.app_name).uppercase(Locale.ENGLISH),
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Welcome Label
        Text(
            text = "Hello, Welcome back!",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Week Label
        Streaks(streak, highlightedDay)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Today's Workout", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(3.dp))

        TodayWorkoutSummaryCard(viewModel = viewModel)

        // Streak Label
        NewStreak(streak)
        Spacer(modifier = Modifier.height(16.dp))

        // 🦥 Sloth Level Image Based on Streak Weeks
        val slothImage = when {
            streak < 21 -> R.drawable.level2 // 0–2 weeks
            streak < 49 -> R.drawable.level1 // 3–6 weeks
            else -> R.drawable.level3        // 7+ weeks
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = slothImage),
                contentDescription = "Sloth Level",
                modifier = Modifier
                    .height(400.dp)
                    .clip(RoundedCornerShape(30.dp))
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController(), viewModel = FakeExerciseViewModel())
}
