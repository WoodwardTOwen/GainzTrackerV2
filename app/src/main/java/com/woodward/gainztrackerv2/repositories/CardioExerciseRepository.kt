package com.woodward.gainztrackerv2.repositories

import com.woodward.gainztrackerv2.database.dao.CardioExerciseDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardioExerciseRepository @Inject constructor(val dao: CardioExerciseDao){

}