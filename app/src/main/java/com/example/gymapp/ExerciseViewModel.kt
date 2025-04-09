package com.example.gymapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapp.data.ApiKey
import com.example.gymapp.data.ExerciseDBAPI.RetrofitInstance
import com.example.gymapp.data.ExerciseDbItem
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.util.Log


open class ExerciseViewModel : ViewModel() {
    private val apiKey = ApiKey.apiKey

    val _exercises = MutableStateFlow<List<ExerciseDbItem>>(emptyList())
    val exercises: StateFlow<List<ExerciseDbItem>> = _exercises

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _categories = MutableStateFlow<List<String?>>(emptyList())
    val categories: StateFlow<List<String?>> = _categories

    private val _bodyPartExercises = MutableStateFlow<List<ExerciseDbItem>>(emptyList())
    val bodyPartExercises: StateFlow<List<ExerciseDbItem>> = _bodyPartExercises


    private val api = RetrofitInstance.api // You’ll create this in Step 3

    fun fetchExercises() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = api.getAllExercises(apiKey = apiKey)
                _exercises.value = response
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getBodyPartCategory() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = api.getBodyPartCategory(apiKey = apiKey)
                _categories.value = response
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getBodyPartExercises(bodyPart: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d("ExerciseViewModel", "getting list of exercises from api")
                val response = api.getExercisesByBodyPart(
                    apiKey = apiKey,
                    bodyPart = bodyPart
                )
                Log.d("ExerciseViewModel", "response: $response")
                _bodyPartExercises.value = response
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
