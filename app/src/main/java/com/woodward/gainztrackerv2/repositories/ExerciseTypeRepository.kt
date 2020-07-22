package com.woodward.gainztrackerv2.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.woodward.gainztrackerv2.database.dao.ExerciseTypeDao
import com.woodward.gainztrackerv2.database.entity.Category
import com.woodward.gainztrackerv2.database.entity.ExerciseType
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseTypeRepository @Inject constructor(val dao: ExerciseTypeDao) {

    @WorkerThread
    fun getExerciseTypeList(id: Int) : LiveData<List<ExerciseType?>> {
        Timber.i("Get Exercise Type List Called")
        return dao.getExerciseTypeList(id)
    }

    @WorkerThread
    suspend fun deleteExerciseType(exerciseType: ExerciseType) {
        Timber.i("Delete ExerciseType Called")
        dao.Delete(exerciseType)
    }
}