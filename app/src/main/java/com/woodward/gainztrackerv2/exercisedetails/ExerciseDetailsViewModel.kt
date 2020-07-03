package com.woodward.gainztrackerv2.exercisedetails

import android.app.Application
import androidx.lifecycle.*
import com.woodward.gainztrackerv2.repository.ExerciseRepository

//If context is required in the viewmodel -> change to extend androidviewmodel
class ExerciseDetailsViewModel (repository: ExerciseRepository, application: Application) : ViewModel() {

    //Job and database reference here

    /**
     * Requires Repository methods to be implemented first
     */

    /*private val _ExerciseData = MutableLiveData<ExerciseDetails>()
    val exerciseData: LiveData<ExerciseDetails> = Transformations.switchMap(_ExerciseData) {
        exerciseData -> ExerciseRepository
    }*/

    /**
     * For the current time in the user application state
     */
    private val _currentTime = MutableLiveData<String>()
    val currentTime: LiveData<String>
        get() = _currentTime

    init {

    }

    //May need changing
    override fun onCleared() {
        super.onCleared()
        ExerciseRepository.CancelJobs()
    }

    /**
     * Kept here just in case if the above overridden method doesn't operate correctly
     */
   /* fun cancenJobs() {
        ExerciseRepository.CancelJobs()
    }*/
}