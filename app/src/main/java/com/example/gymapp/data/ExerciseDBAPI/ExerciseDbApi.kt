package com.example.gymapp.data.ExerciseDBAPI

import com.example.gymapp.data.ExerciseDbItem
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ExerciseDbApi {
    @GET("/exercises")
    suspend fun getAllExercises(
        @Header("X-RapidAPI-Key") apiKey: String,
        @Header("X-RapidAPI-Host") host: String = "exercisedb.p.rapidapi.com"
    ): List<ExerciseDbItem>

    @GET("exercises/bodyPartList")
    suspend fun getBodyPartCategory(
        @Header("X-RapidAPI-Key") apiKey: String,
        @Header("X-RapidAPI-Host") host: String = "exercisedb.p.rapidapi.com"
    ): List<String>

    @GET("exercises/bodyPart/{bodyPart}")
    suspend fun getExercisesByBodyPart(
        @Header("X-RapidAPI-Key") apiKey: String,
        @Header("X-RapidAPI-Host") host: String = "exercisedb.p.rapidapi.com",
        @Path("bodyPart") bodyPart: String
    ): List<ExerciseDbItem>
}
