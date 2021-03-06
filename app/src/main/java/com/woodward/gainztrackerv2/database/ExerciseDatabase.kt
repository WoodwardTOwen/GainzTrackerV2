package com.woodward.gainztrackerv2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.woodward.gainztrackerv2.database.dao.CardioExerciseDao
import com.woodward.gainztrackerv2.database.dao.CategoryDao
import com.woodward.gainztrackerv2.database.dao.ExerciseTypeDao
import com.woodward.gainztrackerv2.database.dao.WeightedExerciseDao
import com.woodward.gainztrackerv2.database.entity.CardiovascularExerciseData
import com.woodward.gainztrackerv2.database.entity.Category
import com.woodward.gainztrackerv2.database.entity.ExerciseType
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData


@Database(
    entities = [WeightedExerciseData::class, ExerciseType::class, Category::class, CardiovascularExerciseData::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ExerciseDatabase : RoomDatabase() {
    abstract fun WeightedExerciseDao(): WeightedExerciseDao
    abstract fun ExerciseType(): ExerciseTypeDao
    abstract fun CategoryDao(): CategoryDao
    abstract fun CardioExerciseDao() : CardioExerciseDao
}
