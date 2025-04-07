package com.example.gymapp.HomeScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.Components.Streaks
import com.example.gymapp.R
import com.example.gymapp.data.eDayOfWeek
import java.util.Locale

@Composable
fun HomeScreen(
    navController: NavController
) {
    val streak: Int = 186
    val dayOfWeek: eDayOfWeek = eDayOfWeek.MONDAY
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

        // Placeholder
        Text(
            // need to change the week number string to be dynamic
            text = stringResource(R.string.Week_Number).uppercase(Locale.ENGLISH),
            color = Color.Black,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1
        )
        Text(text = "Streak", style = MaterialTheme.typography.bodyLarge)
        Streaks(streak, dayOfWeek)
        Text(text = "Today's Workout", style = MaterialTheme.typography.bodyLarge)
        Text(text = "My Week", style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    // Provide a fake navController for preview
    HomeScreen(navController = rememberNavController())
}