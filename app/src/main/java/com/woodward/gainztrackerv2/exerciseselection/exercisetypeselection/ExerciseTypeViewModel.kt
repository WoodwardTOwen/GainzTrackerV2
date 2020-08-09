package com.woodward.gainztrackerv2.exerciseselection.exercisetypeselection

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.woodward.gainztrackerv2.database.entity.ExerciseType
import com.woodward.gainztrackerv2.repositories.ExerciseTypeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class ExerciseTypeViewModel @ViewModelInject constructor (val repository: ExerciseTypeRepository) : ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _catID = MutableLiveData<Int>()
    val catID : LiveData<Int>
        get() = _catID

    private val _isCardio = MutableLiveData<Boolean>()
    val isCardio : LiveData<Boolean>
        get() = _isCardio

    private val _exerciseName = MutableLiveData<String>()
    val exerciseName: LiveData<String>
        get() = _exerciseName

    /**
     * Might need to store the date temporarily too
     *          Do Not Reset it though -> in case the user goes back and forth
     */


    val exerciseTypeList : LiveData<List<ExerciseType>> = Transformations.switchMap(catID) {
         repository.getExerciseTypeList(catID.value!!)
    }


    private val _navigateToWeightExerciseDetails = MutableLiveData<Boolean>()
    val navigateToWeightExerciseDetails: LiveData<Boolean>
        get() = _navigateToWeightExerciseDetails

    /**
     * May not be needed for this portion of the application
     *
     * Unless the date is needed to be passed in here
     */

    suspend fun deleteExerciseType(exerciseType: ExerciseType) = viewModelScope.launch (Dispatchers.IO) {
        repository.deleteExerciseType(exerciseType)
    }

    fun doneNavigatingToWeightExerciseDetails() {
        _navigateToWeightExerciseDetails.value = null
    }

    fun setNavigation(cardio: Boolean) {
        _navigateToWeightExerciseDetails.value = cardio
    }

    fun setCatID (id: Int) {
        this._catID.value = id
    }

    fun resetCatID() {
        this._catID.value = null
    }

    fun setIsCardio(cardio: Boolean){
        this._isCardio.value = cardio
    }

    fun setExerciseName(name: String) {
        this._exerciseName.value = name
    }

    fun resetCardio() {
        this._isCardio.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}