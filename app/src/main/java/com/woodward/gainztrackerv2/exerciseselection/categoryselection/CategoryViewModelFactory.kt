package com.woodward.gainztrackerv2.exerciseselection.categoryselection

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class CategoryViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class - CategoryViewModel")
    }
}