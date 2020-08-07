package com.woodward.gainztrackerv2.exercisedetails.weights

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.woodward.gainztrackerv2.database.ExerciseDatabase
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData
import com.woodward.gainztrackerv2.repositories.ExerciseRepository
import kotlinx.coroutines.*
import java.util.*

class ExerciseDetailsViewModelWeights @ViewModelInject constructor(val repository: ExerciseRepository) :
    ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private val _weightEntered = MutableLiveData<Double>(0.0)
    val weightEntered: LiveData<Double>
        get() = _weightEntered

    private val _repsEntered = MutableLiveData(0)
    val repsEntered: LiveData<Int>
        get() = _repsEntered

    private val _rpeEntered = MutableLiveData(0)
    val rpeEntered: LiveData<Int>
        get() = _rpeEntered

    /**
     * For the current time in the user application state
     */
    private val _currentDate = MutableLiveData<String>()
    val currentDate: LiveData<String>
        get() = _currentDate

    /**
     * Initializer Block for repos and data source
     */

    fun setDate(date: String) {
        _currentDate.value = date
    }

    fun setWeight(weight: Double) {
        TODO("NEEDS CHECKER")
    }

    fun setReps(reps: Int) {
        TODO("NEEDS CHECKER")
    }

    fun setRPE(rpe: Int) {
        TODO("NEEDS CHECKER")
    }

    /**
     * Controls Weight Related Buttons
     */

    fun incrementWeight() {
        _weightEntered.value = (weightEntered.value)?.plus(2.5)
    }

    fun decrementWeight() {
        TODO("NEEDS CHECKER >= 2.5 Problem")
    }

    /**
     * Controls Reps Related Buttons
     */

    fun incrementReps() {
        _repsEntered.value = (repsEntered.value)?.plus(1)
    }

    fun decrementReps() {
        TODO("NEEDS CHECKER >= 2.5 Problem")
    }

    /**
     * Controls RPE Related Buttons
     */

    fun incrementRPE() {
        _rpeEntered.value = (rpeEntered.value)?.plus(1)
    }

    fun decrementRPE() {
        TODO("NEEDS CHECKER >= 2.5 Problem")
    }


    /***
     * DATABASE INTERACTIONS
     */

    /**
     * Insert Data to DB
     */
    suspend fun insertData(weightData: WeightedExerciseData) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertWeightExerciseData(weightData)
        }

    /**
     * Updates Data in DB
     */
    suspend fun updateData(weightData: WeightedExerciseData) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWeightExerciseData(weightData)
        }

    /**
     * Deletes Data from DB
     */
    suspend fun deleteData(weightData: WeightedExerciseData) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWeightExerciseData(weightData)
        }

    /**
     * Deletes All Data in conjunction with [name] and [date]
     */

    suspend fun deleteAllDelete(name: String, date: String) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteListOFWeightExerciseData(name, date)
        }


    fun onSubmit() {
        viewModelScope.launch {

            /**
             * Remember to switch to I/O thread when inserting data to the db
             */

        }
    }

    /**
     * Clears up the jobs so the coroutine wont be stuck in the background if the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}