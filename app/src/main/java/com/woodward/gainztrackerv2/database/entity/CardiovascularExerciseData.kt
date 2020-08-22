package com.woodward.gainztrackerv2.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cardio_Exercise_Data_Table")
data class CardiovascularExerciseData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val cardio_exerciseID: Int = 0,
    @ColumnInfo(name = "Name")
    val exerciseName: String?,
    var time: String,
    @ColumnInfo(name = "Unit_Of_Measurement")
    var unit: String,
    var distance: Float,
    var sets: Int,
    var date: String
)