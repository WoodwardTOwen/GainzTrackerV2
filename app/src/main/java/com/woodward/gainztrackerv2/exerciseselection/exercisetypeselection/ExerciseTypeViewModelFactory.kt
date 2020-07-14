package com.woodward.gainztrackerv2.exerciseselection.exercisetypeselection

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.woodward.gainztrackerv2.exerciseselection.categoryselection.CategoryViewModel

@Suppress("UNCHECKED_CAST")
class ExerciseTypeViewModelFactory(private val application: Application, private val catId: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseTypeViewModel::class.java)) {
            return ExerciseTypeViewModel(
                application, catId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class - ExerciseTypeViewModel")
    }
}