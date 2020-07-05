package com.woodward.gainztrackerv2.utils

import android.app.Application
import com.woodward.gainztrackerv2.database.ExerciseDatabase
import com.woodward.gainztrackerv2.exercisedetails.weights.ExerciseDetailsViewModelFactoryWeights
import com.woodward.gainztrackerv2.main.MainUIViewModelFactory
import com.woodward.gainztrackerv2.repository.ExerciseRepository

object Injection {
    fun provideExerciseDetailsViewModelFactory(application: Application) : ExerciseDetailsViewModelFactoryWeights {
        return ExerciseDetailsViewModelFactoryWeights(application)
    }

    fun provideMainUIViewModelFactory(application: Application) : MainUIViewModelFactory {
        return MainUIViewModelFactory(application)
    }
}