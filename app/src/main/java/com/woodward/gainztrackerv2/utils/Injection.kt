package com.woodward.gainztrackerv2.utils

import android.app.Application
import com.woodward.gainztrackerv2.exercisedetails.weights.ExerciseDetailsViewModelFactoryWeights
import com.woodward.gainztrackerv2.exerciseselection.categoryselection.CategoryViewModelFactory
import com.woodward.gainztrackerv2.main.MainUIViewModelFactory

object Injection {
    fun provideExerciseDetailsViewModelFactory(application: Application) : ExerciseDetailsViewModelFactoryWeights {
        return ExerciseDetailsViewModelFactoryWeights(application)
    }

    fun provideMainUIViewModelFactory(application: Application) : MainUIViewModelFactory {
        return MainUIViewModelFactory(application)
    }

    fun provideCategoryViewModelFactory(application: Application) : CategoryViewModelFactory {
        return CategoryViewModelFactory(
            application
        )
    }
}