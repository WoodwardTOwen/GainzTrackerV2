package com.woodward.gainztrackerv2.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.woodward.gainztrackerv2.repository.ExerciseRepository

class MainUIViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainUIViewModel::class.java)) {
            return MainUIViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class - MainUIViewModel")
    }
}