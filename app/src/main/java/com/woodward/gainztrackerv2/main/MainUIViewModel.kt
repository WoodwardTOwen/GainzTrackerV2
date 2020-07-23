package com.woodward.gainztrackerv2.main

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.woodward.gainztrackerv2.database.ExerciseDatabase
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData
import com.woodward.gainztrackerv2.repositories.ExerciseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainUIViewModel @ViewModelInject constructor(val repository: ExerciseRepository) : ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * For the current time in the user application state
     */
    private val _currentDate = MutableLiveData<String>()
    val currentDate: LiveData<String>
        get() = _currentDate

    /**
     * Needs changing also -> manual input currently -> only used for acceptance testing
     */
    private val _navigateToExerciseDetailsWeights = MutableLiveData<String>("16-02-20")
    val navigateToExerciseData : LiveData<String>
        get() = _navigateToExerciseDetailsWeights

    val exerciseData: LiveData<List<WeightedExerciseData?>> = Transformations.switchMap(currentDate) {
            currentDate -> repository.getNameAndSetsForDateMainUI(currentDate)
    }

    fun setDate(date: String) {
        _currentDate.value = date
    }

    /**
     * Methods [onExerciseDetailNavigated] and [onExerciseDetailsClicked] used for handling onClick management
     * within the main recyclerview within the MainUI
     */

    fun onExerciseDetailsClicked (name: String) {
        _navigateToExerciseDetailsWeights.value = name
    }

    fun onExerciseDetailNavigated() {
        _navigateToExerciseDetailsWeights.value = null
    }


    /**
     * Deletes list of data from the database based on specified conditions [name] and [date]
     */

    suspend fun deleteListOfExerciseData(name: String, date: String) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteListOFWeightExerciseData(name, date)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}