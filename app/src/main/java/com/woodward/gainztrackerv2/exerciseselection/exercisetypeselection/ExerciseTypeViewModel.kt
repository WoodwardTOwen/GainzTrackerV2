package com.woodward.gainztrackerv2.exerciseselection.exercisetypeselection

import android.app.Application
import androidx.lifecycle.LiveData
import com.woodward.gainztrackerv2.database.ExerciseDatabase
import com.woodward.gainztrackerv2.database.entity.ExerciseType
import com.woodward.gainztrackerv2.repository.ExerciseRepository

class ExerciseTypeViewModel (application: Application, catID: Int) {

    val datasource : ExerciseDatabase = ExerciseDatabase.getInstance(application)
    val repository : ExerciseRepository

    val exerciseTypeList : LiveData<List<ExerciseType?>>

    init {
        repository = ExerciseRepository.getInstance(datasource)
        exerciseTypeList = repository.getExerciseTypeList(catID)
    }


}