package com.woodward.gainztrackerv2.exercisedetails.weights

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData
import com.woodward.gainztrackerv2.repositories.ExerciseRepository
import kotlinx.coroutines.*

class ExerciseDetailsViewModelWeights @ViewModelInject constructor(val repository: ExerciseRepository) :
    ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val weightEntered = MutableLiveData<Double>(0.0)
    val repsEntered = MutableLiveData(0)
    val rpeEntered = MutableLiveData(0)

    private val _exerciseName = MutableLiveData<String>()
    val exerciseName: LiveData<String>
        get() = _exerciseName

    private val _isCardio = MutableLiveData<Boolean>()
    val isCardio: LiveData<Boolean>
        get() = _isCardio

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

    fun setIsCardio(cardio: Boolean) {
        _isCardio.value = cardio
    }

    fun setExerciseName(name: String) {
        _exerciseName.value = name
    }

    fun getWeightEntered() : Double? {
        return weightEntered.value
    }

    /**
     * Controls Weight Related Buttons
     */

    fun incrementWeight() {
        weightEntered.value = (weightEntered.value)?.plus(2.5)
    }

    fun decrementWeight() {
        if (weightEntered.value!! >= 2.5) {
            weightEntered.value = (weightEntered.value)?.minus(2.5)
        }
    }

    /**
     * Controls Reps Related Buttons
     */

    fun incrementReps() {
        repsEntered.value = (repsEntered.value)?.plus(1)
    }

    fun decrementReps() {
        if (repsEntered.value!! >= 1) {
            repsEntered.value = (repsEntered.value)?.minus(1)
        }
    }

    /**
     * Controls RPE Related Buttons
     */

    fun incrementRPE() {
        rpeEntered.value = (rpeEntered.value)?.plus(1)
    }

    fun decrementRPE() {
        if (rpeEntered.value!! >= 1) {
            rpeEntered.value = (rpeEntered.value)?.minus(1)
        }
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

    private suspend fun getSetsForNameAndDate(name: String, date: String): Int {
        return repository.getSetsAmountForNameAndDate(name, date)
    }

    private suspend fun updateSetsForData(sets: Int, name: String, date: String) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSetsFromNameAndDate(sets, name, date)
        }

    /**
     * Button function for applying the data to the DB
     */

    fun onSubmit() {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                val sets = getSetsForNameAndDate(exerciseName.value!!, currentDate.value!!) + 1
                insertData(
                    WeightedExerciseData(
                        date = exerciseName.value!!,
                        exerciseName = exerciseName.value!!,
                        reps = repsEntered.value!!,
                        rpe = rpeEntered.value!!,
                        weight = weightEntered.value!!,
                        sets = sets
                    )
                )
                /**
                 * May need changing to the list of ID's Instead
                 */
                updateSetsForData(sets, exerciseName.value!!, currentDate.value!!)
            }


            TODO("NEEDS CHECKERS BEFORE SUBMITTING")
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