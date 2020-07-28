package com.woodward.gainztrackerv2.exerciseselection.exercisetypeselection.newexercisetype

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.woodward.gainztrackerv2.database.entity.Category
import com.woodward.gainztrackerv2.database.entity.ExerciseType
import com.woodward.gainztrackerv2.repositories.ExerciseTypeRepository
import com.woodward.gainztrackerv2.utils.isNullOrWhiteSpace
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NewExerciseTypeViewModel @ViewModelInject constructor(val repository: ExerciseTypeRepository) :
    ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _isCardio = MutableLiveData<Boolean>()
    val isCardio: LiveData<Boolean>
        get() = _isCardio

    private val _storedCatID = MutableLiveData<Int>()
    val storedCatID: LiveData<Int>
        get() = _storedCatID

    private val _transactionCompleted = MutableLiveData<Boolean>()
    val transCompleted : LiveData<Boolean>
        get() = _transactionCompleted

    private val _snackBarNullOrBlank = MutableLiveData<Boolean>()
    val snackBarNullOrBlank : LiveData<Boolean>
        get() = _snackBarNullOrBlank

    private val _snackBarAlreadyExists = MutableLiveData<Boolean>()
    val snackBarAlreadyExists : LiveData<Boolean>
        get() = _snackBarAlreadyExists

    private suspend fun insertNewExerciseType(exerciseType: ExerciseType) = repository.insertExerciseType(exerciseType)

    private suspend fun checkIfNameExists(name: String?, catID: Int) = repository.checkIfExerciseTypeExists(name, catID)

    /**
     *
     * Need to somehow pull over a bundle containing the ID of the Cat to submit the exercise
     *
     */

    fun onSubmit(name: String) {

        viewModelScope.launch (Dispatchers.IO) {

            when {
                isNullOrWhiteSpace(name) -> {
                    _snackBarNullOrBlank.value = true
                }
                checkIfNameExists(name, storedCatID.value!!) -> {
                    _snackBarAlreadyExists.value = true
                }
                else -> {
                    val newExerciseType = ExerciseType(
                        exerciseTypeName = name,
                        categoryID = storedCatID.value!!,
                        isCardio = isCardio.value!!
                    )
                    insertNewExerciseType(newExerciseType)
                    _transactionCompleted.value = true
                    resetCatID()
                }
            }
        }


        /**
         * Once successfully submitted, need to reset the catID that has been passed over
         */
    }

    /**
     * Manipulation of the passed over bundle category ID
     */
    fun setCatID(catID: Int) {
        _storedCatID.value = catID
    }

    fun resetCatID() {
        _storedCatID.value = null
    }

    /**
     * Control whether the exercise is a cardiovascular exercise or not
     */
    fun setCardio(cardio: Boolean) {
        _isCardio.value = cardio
    }

    fun resetCardio() {
        _isCardio.value = null
    }

    /**
     * SnackBar control for Null and Blank Inputs
     */
    fun doneShowingNullorBlankSnackBar() {
        _snackBarNullOrBlank.value = null
    }


    /**
     * SnackBar control for already existing names in the entity table
     */

    fun doneShowingAlreadyExistsSnackBar() {
        _snackBarAlreadyExists.value = null
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}