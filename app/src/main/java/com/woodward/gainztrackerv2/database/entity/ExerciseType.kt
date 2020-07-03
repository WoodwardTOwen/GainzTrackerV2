package com.woodward.gainztrackerv2.database.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "ExerciseTypeTable",
    indices = [Index(value = ["Category_ID_FK"])],
    foreignKeys = [ForeignKey(entity = Category::class,
    parentColumns = ["Category_ID"],
    childColumns = ["Category_ID_FK"], onDelete = CASCADE, onUpdate = CASCADE)])

data class ExerciseType (
    @PrimaryKey(autoGenerate = true)
    var exerciseTypeID: Int,
    var exerciseTypeName: String,
    @ColumnInfo(name = "Category_ID_FK")
    var categoryID: Int
)