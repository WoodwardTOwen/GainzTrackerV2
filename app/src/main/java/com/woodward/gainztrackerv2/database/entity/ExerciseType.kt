package com.woodward.gainztrackerv2.database.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "Exercise_Type_Table",
    indices = [Index(value = ["Category_ID_FK"])],
    foreignKeys = [ForeignKey(entity = Category::class,
    parentColumns = ["Category_ID"],
    childColumns = ["Category_ID_FK"], onDelete = CASCADE, onUpdate = CASCADE)])

data class ExerciseType (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var exerciseTypeID: Int =0,
    @ColumnInfo(name = "Name")
    var exerciseTypeName: String ="",
    @ColumnInfo(name = "Category_ID_FK")
    var categoryID: Int = 0,
    @ColumnInfo(name = "Is_Cardio")
    var isCardio: Boolean = false
)