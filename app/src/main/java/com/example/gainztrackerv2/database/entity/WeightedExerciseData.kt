package com.example.gainztrackerv2.database.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.sql.Date


data class WeightedExerciseData(
    @PrimaryKey(autoGenerate = true)
    var exerciseId: Long = 0L,
    @ColumnInfo(name = "exercise_name")
    val exerciseName: String?,

    val date: Date?,


    //HELLO TEST TEST TEST
    var weight: Double,
    @ColumnInfo(name = "repetitions")
    var reps: Int,

    var rpe: Int,

    var sets: Int
)
