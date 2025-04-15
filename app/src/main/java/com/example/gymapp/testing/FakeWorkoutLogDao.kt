package com.example.gymapp.testing

import com.example.gymapp.data.WorkoutLog
import com.example.gymapp.data.WorkoutLogDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

class FakeWorkoutLogDao : WorkoutLogDao {
    private val logs = mutableListOf<WorkoutLog>()

    override suspend fun getLogForExercise(date: LocalDate, exerciseId: String): WorkoutLog? {
        return logs.find { it.date == date && it.exerciseId == exerciseId }
    }

    override suspend fun updateLog(log: WorkoutLog) {
        logs.replaceAll { if (it.exerciseId == log.exerciseId && it.date == log.date) log else it }
    }

    override suspend fun insertLog(log: WorkoutLog) {
        logs.add(log)
    }

    override fun getLogsForDate(date: LocalDate): Flow<List<WorkoutLog>> {
        return flowOf(logs.filter { it.date == date })
    }
    override suspend fun deleteLog(date: LocalDate, exerciseId: String) {
        logs.removeAll { it.date == date && it.exerciseId == exerciseId }
    }
}
