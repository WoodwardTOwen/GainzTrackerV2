package com.woodward.gainztrackerv2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.woodward.gainztrackerv2.database.dao.ExerciseTypeDao
import com.woodward.gainztrackerv2.database.dao.WeightedExerciseDao
import com.woodward.gainztrackerv2.database.entity.CardiovascularExerciseData
import com.woodward.gainztrackerv2.database.entity.Category
import com.woodward.gainztrackerv2.database.entity.ExerciseType
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData

@Database(
    entities = [WeightedExerciseData::class, ExerciseType::class, Category::class, CardiovascularExerciseData::class],
    version = 2,
    exportSchema = false
)
abstract class ExerciseDatabase : RoomDatabase() {

    //Dao Declaration goes here
    abstract fun WeightedExerciseDao() : WeightedExerciseDao
    abstract fun ExerciseType() : ExerciseTypeDao

    companion object {
        @Volatile //Helps ensure data is always up to date, writes and reads from main memory instead of cache
        private var INSTANCE: ExerciseDatabase? = null
        fun getInstance(context: Context): ExerciseDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ExerciseDatabase::class.java,
                        "exercise_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}