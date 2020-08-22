package com.woodward.gainztrackerv2.exercisedetails.cardio

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.woodward.gainztrackerv2.repositories.CardioExerciseRepository

class ExerciseDetailsCardioViewModel @ViewModelInject constructor(val repositoryFactory: CardioExerciseRepository) :
    ViewModel(){

}