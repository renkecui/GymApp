package com.example.gymapp.data.ExerciseDBAPI

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Logs headers + body
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://exercisedb.p.rapidapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val api: ExerciseDbApi = retrofit.create(ExerciseDbApi::class.java)


}
