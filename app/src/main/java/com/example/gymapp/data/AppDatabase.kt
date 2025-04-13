package com.example.gymapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [SelectedExerciseEntity::class, WorkoutLog::class, WorkoutNotes::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun selectedExerciseDao(): SelectedExerciseDao
    abstract fun workoutLogDao(): WorkoutLogDao
    abstract fun workoutNotesDao(): WorkoutNotesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gym_app_database"
                )
                .fallbackToDestructiveMigration() // This will clear the database on version change
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 