package com.woodward.gainztrackerv2.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.woodward.gainztrackerv2.main.adapters.groupie.WeightItem

@Entity(tableName = "Weight_Exercise_Data_Table")
data class WeightedExerciseData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var exerciseId: Int = 0,
    @ColumnInfo(name = "Name")
    val exerciseName: String?,
    val date: String?,
    var weight: Double,
    @ColumnInfo(name = "repetitions")
    var reps: Int,
    var rpe: Int,
    var sets: Int
)

