package com.woodward.gainztrackerv2.database.entity

import androidx.room.Entity

@Entity(tableName = "Cardio_Exercise_Data_Table")
data class CardiovascularExerciseData(
    val exerciseID: Int
)