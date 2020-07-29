package com.woodward.gainztrackerv2.exerciseselection.exercisetypeselection.newexercisetype

import androidx.databinding.Bindable
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

    /**
     * Manages whether the switch was turned to a cardiovascular format or not
     */

    private val _cardio = MutableLiveData<Boolean>(false)
    val cardio: LiveData<Boolean>
        get() = _cardio

    /**
     * Stores the category ID -> used for the FK for creating the new exerciseType
     */
    private val _storedCatID = MutableLiveData<Int>()
    val storedCatID: LiveData<Int>
        get() = _storedCatID

    /**
     * Used for display in the title for the Add New Exercise Type section
     */
    private val _storedCategoryName = MutableLiveData<String>()
    val storedCategoryName: LiveData<String>
        get() = _storedCategoryName

    /**
     * Alerts the fragment transaction as complete
     */

    private val _transactionCompleted = MutableLiveData<Boolean>()
    val transCompleted: LiveData<Boolean>
        get() = _transactionCompleted

    /**
     * Manages the SnackBar Messages that are shown to the user
     */

    private val _snackBarNullOrBlank = MutableLiveData<Boolean>()
    val snackBarNullOrBlank: LiveData<Boolean>
        get() = _snackBarNullOrBlank

    private val _snackBarAlreadyExists = MutableLiveData<Boolean>()
    val snackBarAlreadyExists: LiveData<Boolean>
        get() = _snackBarAlreadyExists

    private suspend fun insertNewExerciseType(exerciseType: ExerciseType) =
        repository.insertExerciseType(exerciseType)

    private suspend fun checkIfNameExists(name: String?, catID: Int) =
        repository.checkIfExerciseTypeExists(name, catID)

    private suspend fun getNameForCategory(id: Int) = repository.getCategoryNameByID(id)


    /**
     * Runs on the main thread -> compiler doesn't allow setCategory to be ran on a background thread
     */
    fun getNameForID (id : Int) {
        viewModelScope.launch {
            setCategoryName(getNameForCategory(id))
        }
    }

    /**
     *
     * Need to somehow pull over a bundle containing the ID of the Cat to submit the exercise
     *
     */

    fun onSubmit(name: String) {

        viewModelScope.launch(Dispatchers.IO) {

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
                        isCardio = cardio.value!!
                    )
                    insertNewExerciseType(newExerciseType)
                    _transactionCompleted.value = true
                    reset()
                }
            }
        }
    }

    /**
     * Resets everything once transaction is complete
     */
    private fun reset() {
        resetCatID()
        resetCategoryName()
        resetCardio()
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
        _cardio.value = cardio
    }

    fun resetCardio() {
        _cardio.value = null
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

    fun setCategoryName(name: String) {
        _storedCategoryName.value = name
    }

    fun resetCategoryName() {
        _storedCategoryName.value = null
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}