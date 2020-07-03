package com.woodward.gainztrackerv2.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category_Table")
data class Category (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Category_ID")
    var categoryID: Int?,
    var categoryName: String
)