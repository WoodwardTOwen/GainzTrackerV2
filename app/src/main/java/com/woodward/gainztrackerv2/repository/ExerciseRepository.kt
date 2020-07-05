package com.woodward.gainztrackerv2.repository

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.woodward.gainztrackerv2.database.ExerciseDatabase
import com.woodward.gainztrackerv2.database.dao.WeightedExerciseDao
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData
import kotlinx.coroutines.*
import java.lang.Exception


//Object to declare it as a singleton
class ExerciseRepository private constructor(private val database: ExerciseDatabase) {


    companion object {
        @Volatile
        private var instance: ExerciseRepository? = null

        fun getInstance(database: ExerciseDatabase) = instance ?: synchronized(this) {
            instance ?: ExerciseRepository(database).also { newInstance -> instance = newInstance }
        }
    }

    /**
     * Coroutine to insert the data into database
     */
    @WorkerThread //Used to not interrupt the main UI thread
    suspend fun insertWeightExerciseData(weightData: WeightedExerciseData) = withContext(Dispatchers.IO) {
        database.WeightedExerciseDao().Insert(weightData)
    }

    /**
     * Coroutine to insert data into database
     */
    @WorkerThread
    suspend fun updateWeightExerciseData(weightData: WeightedExerciseData) = withContext(Dispatchers.IO){
        database.WeightedExerciseDao().Update(weightData)
    }

}