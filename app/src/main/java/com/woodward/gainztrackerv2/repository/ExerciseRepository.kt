package com.woodward.gainztrackerv2.repository

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.woodward.gainztrackerv2.database.ExerciseDatabase
import com.woodward.gainztrackerv2.database.dao.WeightedExerciseDao
import com.woodward.gainztrackerv2.database.entity.Category
import com.woodward.gainztrackerv2.database.entity.ExerciseType
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData
import kotlinx.coroutines.*
import timber.log.Timber
import java.lang.Exception
import java.util.*


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
        Timber.d("Insert DB called")
        database.WeightedExerciseDao().Insert(weightData)
    }

    /**
     * Coroutine to insert data into database
     */
    @WorkerThread
    suspend fun updateWeightExerciseData(weightData: WeightedExerciseData) = withContext(Dispatchers.IO){
        Timber.d("Update DB called")
        database.WeightedExerciseDao().Update(weightData)
    }

    /**
     * Coroutine to delete singular pieces of data from the db
     */
    @WorkerThread
    suspend fun deleteWeightExerciseData(weightData: WeightedExerciseData) = withContext(Dispatchers.IO) {
        Timber.d("Delete DB called")
        database.WeightedExerciseDao().Delete(weightData)
    }

    /**
     * Coroutine to delete multiple pieces of data from the db
     */
    @WorkerThread
    suspend fun deleteListOFWeightExerciseData(exerciseName: String, date: String) = withContext(Dispatchers.IO){
        Timber.d("DeleteList DB called")
        database.WeightedExerciseDao().DeleteMultipleData(exerciseName, date)
    }

    @WorkerThread
    fun getNameAndSetsForDateMainUI (date: String) : LiveData<List<WeightedExerciseData?>> {
        Timber.i("Call for Main UI Recycler View -> name and date info")
        return database.WeightedExerciseDao().GetNameAndSetsForDate(date)
    }

    @WorkerThread
    fun getCategoriesList() : LiveData<List<Category?>> {
        Timber.i("Get Categories Called")
        return database.CategoryDao().getAllCategories()
    }
    @WorkerThread
    suspend fun insertNewCategory(category: Category) = withContext(Dispatchers.IO) {
        Timber.i("Insert New Category Called")
        database.CategoryDao().Insert(category)
    }

    @WorkerThread
    suspend fun deleteCategory(category: Category) = withContext(Dispatchers.IO) {
        Timber.i("Delete Category Called")
        database.CategoryDao().Delete(category)
    }

    @WorkerThread
    fun getExerciseTypeList(id: Int) : LiveData<List<ExerciseType?>> {
        Timber.i("Get Exercise Type List Called")
        return database.ExerciseType().getExerciseTypeList(id)
    }
}