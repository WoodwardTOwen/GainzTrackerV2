package com.woodward.gainztrackerv2.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "Weight_Exercise_Data_Table")
data class WeightedExerciseData(
    @PrimaryKey(autoGenerate = true)
    var exerciseId: Long = 0L,
    @ColumnInfo(name = "exercise_name")
    val exerciseName: String?,
    val date: Date?,
    var weight: Double,
    @ColumnInfo(name = "repetitions")
    var reps: Int,
    var rpe: Int,
    var sets: Int
)
