package com.woodward.gainztrackerv2.exercisedetails.weights

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData
import com.woodward.gainztrackerv2.repositories.ExerciseRepository
import com.woodward.gainztrackerv2.utils.isNullInt
import com.woodward.gainztrackerv2.utils.isNullOrWhiteSpace
import com.woodward.gainztrackerv2.utils.isNumericOrNullDouble
import kotlinx.coroutines.*

class ExerciseDetailsViewModelWeights @ViewModelInject constructor(val repository: ExerciseRepository) :
    ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * For the current time in the user application state
     */
    private val _currentDate = MutableLiveData<String>()
    val currentDate: LiveData<String>
        get() = _currentDate

    private val _exerciseName = MutableLiveData<String>()
    val exerciseName: LiveData<String>
        get() = _exerciseName

    val exerciseData: LiveData<List<WeightedExerciseData?>> =
        Transformations.switchMap(exerciseName) { exerciseName ->
            repository.getListOfWeightResistedDate(exerciseName, currentDate.value!!)
        }

    /**
     * Variables for applying new data to the db -> for the [onSubmit] method
     */

    val weightEntered = MutableLiveData<Double?>()
    val repsEntered = MutableLiveData<Int?>()
    val rpeEntered = MutableLiveData<Int?>()

    private val _isCardio = MutableLiveData<Boolean>()

    /**
     * If [newDataSubmitted] evaluates to true -> onBackPressed will automatically navigate to the main menu
     * else -> onBackPressed will go back to the exercise type due to assumed mistake of choosing
     * the exercise type
     */

    private val newDataSubmitted = MutableLiveData(false)

    private fun setNewDataSubmitted() {
        newDataSubmitted.value = true
    }

    fun getNewDataSubmittedStatus() = newDataSubmitted.value

    fun resetStatusForNewDataSubmitted() {
        newDataSubmitted.value = false
    }

    /**
     * Controlling whether the user has clicked an exercise value or not
     *
     * when [updateRequestedOnRequest] is false -> regular onSubmit Function
     * when [updateRequestedOnRequest] is true -> change button status to update
     */

    private val _updateRequestOnExercise = MutableLiveData<Boolean>()
    val updateRequestedOnRequest: LiveData<Boolean>
        get() = _updateRequestOnExercise


    private val _cachedExerciseData = MutableLiveData<WeightedExerciseData>()


    /** For alerting the user something went wrong*/

    private val _snackBarInvalidInputEvent = MutableLiveData<Boolean>()
    val snackBarInvalidInputEvent: LiveData<Boolean>
        get() = _snackBarInvalidInputEvent

    fun doneShowingSnackBarEventInvalidInput() {
        _snackBarInvalidInputEvent.value = false
    }

    /**For alerting the user that the data has been deleted*/

    private val _snackBarDeletedDataEvent = MutableLiveData<Boolean>()
    val snackBarDeletedDataEvent: LiveData<Boolean>
        get() = _snackBarDeletedDataEvent

    fun doneShowingSnackBarEventDeletedData() {
        _snackBarDeletedDataEvent.value = false
    }

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

    /**
     * Controls Weight Related Buttons
     */

    fun incrementWeight() {
        if (weightEntered.value == null) {
            weightEntered.value = 0.0
        }
        weightEntered.value = (weightEntered.value)?.plus(2.5)
    }

    fun decrementWeight() {
        if (weightEntered.value == null) {
            return
        } else if (weightEntered.value!! >= 2.5) {
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
    private suspend fun insertData(weightData: WeightedExerciseData) =
        repository.insertWeightExerciseData(weightData)


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
        repository.deleteListOFWeightExerciseData(name, date)

    private suspend fun getSetsForNameAndDate(name: String, date: String): Int? {
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

        when {
            isNumericOrNullDouble(weightEntered.value) || isNullInt(repsEntered.value) || isNullInt(
                rpeEntered.value
            ) -> {
                _snackBarInvalidInputEvent.value = true
            }
            else ->
                viewModelScope.launch(Dispatchers.IO) {
                    var sets = getSetsForNameAndDate(exerciseName.value!!, currentDate.value!!)

                    /**
                     * When the table has no data based on [exerciseName] and [currentDate] (it must be the first exercise for the date)
                     * Thus with no data being available creates a null value -> to prevent this a when block is used so no
                     * 'dirty' data is applied in the db
                     */

                    when (sets) {
                        null -> {
                            sets = 1
                        }
                        else -> {
                            sets += 1
                        }
                    }

                    insertData(
                        WeightedExerciseData(
                            date = currentDate.value!!,
                            exerciseName = exerciseName.value!!,
                            reps = repsEntered.value!!,
                            rpe = rpeEntered.value!!,
                            weight = weightEntered.value!!,
                            sets = sets
                        )
                    )
                    /**
                     * To prevent creating a null run-time exception with DAO and DB
                     */
                    if (sets > 1) {
                        updateSetsForData(sets, exerciseName.value!!, currentDate.value!!)
                    }
                    /**
                     * Switching back to the main thread -> clear the textbox input and start fresh in the UI
                     */
                    withContext(Dispatchers.Main){
                        clearInputBoxes()
                        setNewDataSubmitted()
                    }
                }
        }
    }

    fun onSubmitUpdate(weightData: WeightedExerciseData) {
        when {
            isNumericOrNullDouble(weightEntered.value) || isNullInt(repsEntered.value) || isNullInt(
                rpeEntered.value
            ) -> {
                _snackBarInvalidInputEvent.value = true
            }
            else ->
                viewModelScope.launch(Dispatchers.IO) {
                    updateData(weightData)
                }
        }
    }


    fun onDeleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllDelete(exerciseName.value!!, currentDate.value!!)
        }
        _snackBarDeletedDataEvent.value = true
    }


    /**
     * Field Control for when given actions are created
     */

    private fun clearInputBoxes() {
        weightEntered.value = null
        repsEntered.value = null
        rpeEntered.value = null
    }

    fun onClickSetTextBoxData(weightData: WeightedExerciseData) {
        weightEntered.value = weightData.weight
        repsEntered.value = weightData.reps
        rpeEntered.value = weightData.rpe
    }


    /**
     * Clears up the jobs so the coroutine wont be stuck in the background if the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
        _cachedExerciseData.value = null
        viewModelJob.cancel()
    }
}