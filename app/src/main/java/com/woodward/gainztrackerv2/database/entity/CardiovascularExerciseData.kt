package com.woodward.gainztrackerv2.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Needs editing -> Not a priority for development right now
@Entity(tableName = "Cardio_Exercise_Data_Table")
data class CardiovascularExerciseData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val exerciseID: Int,
    var time: Int,
    var distance: Float,
    var sets: Int
)