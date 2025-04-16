package com.example.gymapp.viewModel

import com.example.gymapp.data.WorkoutLog
import com.example.gymapp.data.WorkoutLogDao
import com.example.gymapp.data.WorkoutNotes
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

class FakeWorkoutLogDao : WorkoutLogDao {
    var throwError: Boolean = false
    private val logs = mutableListOf<WorkoutLog>()

    private val notesMap = mutableMapOf<LocalDate, WorkoutNotes>()

    suspend fun insertNotes(workoutNotes: WorkoutNotes) {
        notesMap[workoutNotes.date] = workoutNotes
    }

    suspend fun getNotesForDate(date: LocalDate): WorkoutNotes? {
        return notesMap[date]
    }

    override suspend fun getLogForExercise(date: LocalDate, exerciseId: String): WorkoutLog? {
        if (throwError) throw Exception("Test error: getLogForExercise failed")
        return logs.find { it.date == date && it.exerciseId == exerciseId }
    }

    override suspend fun updateLog(log: WorkoutLog) {
        if (throwError) throw Exception("Test error: updateLog failed")
        logs.replaceAll {
            if (it.date == log.date && it.exerciseId == log.exerciseId) log else it
        }
    }

    override suspend fun insertLog(log: WorkoutLog) {
        if (throwError) throw Exception("Test error: insertLog failed")
        logs.add(log)
    }

    override suspend fun deleteLog(date: LocalDate, exerciseId: String) {
        if (throwError) throw Exception("Test error: deleteLog failed")
        logs.removeIf { it.date == date && it.exerciseId == exerciseId }
    }

    override fun getLogsForDate(date: LocalDate) = flow {
        if (throwError) throw Exception("Test error: getLogsForDate failed")
        emit(logs.filter { it.date == date })
    }
    fun clear() {
        logs.clear()
    }
}
