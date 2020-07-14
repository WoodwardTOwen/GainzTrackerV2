package com.woodward.gainztrackerv2.exercisedetails.weights

import android.app.Application
import androidx.lifecycle.*
import com.woodward.gainztrackerv2.database.ExerciseDatabase
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData
import com.woodward.gainztrackerv2.repository.ExerciseRepository
import kotlinx.coroutines.*

//If context is required in the viewmodel -> change to extend androidviewmodel
class ExerciseDetailsViewModelWeights (application: Application) : ViewModel() {

    //Job and database reference here
    private val repository: ExerciseRepository

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * For the current time in the user application state
     */
    private val _currentDate = MutableLiveData<String>()
    val currentDate: LiveData<String>
        get() = _currentDate

    /**
     * Initializer Block for repos and data source
     */
    init {
        val dataSource = ExerciseDatabase.getInstance(application)
        repository = ExerciseRepository.getInstance(dataSource)
    }

    fun setDate(date: String) {
        _currentDate.value = date
    }

    /**
     * Alerts the repository to interact with the database to commit an insert action
     */
    suspend fun insertData(weightData: WeightedExerciseData) = viewModelScope.launch(Dispatchers.IO) {
            repository.insertWeightExerciseData(weightData)
    }

    /**
     * Alerts the repository to interact with the database to commit an update action
     */
    suspend fun updateData(weightData: WeightedExerciseData) = viewModelScope.launch(Dispatchers.IO){
            repository.updateWeightExerciseData(weightData)
    }

    /**
     * Alerts the repository to interact with the database to commit an delete action
     */
    suspend fun deleteData(weightData: WeightedExerciseData) = viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWeightExerciseData(weightData)
    }

    /**
     * Clears up the jobs so the coroutine wont be stuck in the background if the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}