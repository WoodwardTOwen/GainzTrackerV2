package com.woodward.gainztrackerv2.exerciseselection.exercisetypeselection.newexercisetype

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.woodward.gainztrackerv2.repositories.ExerciseTypeRepository
import kotlinx.coroutines.SupervisorJob

class NewExerciseTypeViewModel @ViewModelInject constructor(val repository: ExerciseTypeRepository) : ViewModel() {

    private val viewModelJob = SupervisorJob()


}