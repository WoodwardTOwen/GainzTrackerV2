package com.woodward.gainztrackerv2.exercisedetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.woodward.gainztrackerv2.repository.ExerciseRepository

class ExerciseDetailsViewModelFactory(
    private val repository: ExerciseRepository,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseDetailsViewModel::class.java)) {
            return ExerciseDetailsViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}