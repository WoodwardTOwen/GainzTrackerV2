package com.woodward.gainztrackerv2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.woodward.gainztrackerv2.database.dao.CategoryDao
import com.woodward.gainztrackerv2.database.dao.ExerciseTypeDao
import com.woodward.gainztrackerv2.database.dao.WeightedExerciseDao
import com.woodward.gainztrackerv2.database.entity.CardiovascularExerciseData
import com.woodward.gainztrackerv2.database.entity.Category
import com.woodward.gainztrackerv2.database.entity.ExerciseType
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData
import com.woodward.gainztrackerv2.utils.ioThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors


@Database(
    entities = [WeightedExerciseData::class, ExerciseType::class, Category::class, CardiovascularExerciseData::class],
    version = 2,
    exportSchema = false
)
abstract class ExerciseDatabase : RoomDatabase() {

    //Dao Declaration goes here
    abstract fun WeightedExerciseDao(): WeightedExerciseDao
    abstract fun ExerciseType(): ExerciseTypeDao
    abstract fun CategoryDao() : CategoryDao

    companion object {
        @Volatile //Helps ensure data is always up to date, writes and reads from main memory instead of cache
        private var INSTANCE: ExerciseDatabase? = null

        fun getInstance(context: Context): ExerciseDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ExerciseDatabase::class.java,
                "exercise_database"
            ).fallbackToDestructiveMigration()
                .build()

    }

}


/*
// prepopulate the database after onCreate was called
.addCallback(object : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        // insert the data on the IO Thread

        ioThread {
            ExerciseDatabase.getInstance(context).WeightedExerciseDao()
                .Insert(WeightedExerciseData(1, "BicepCurl", "16-08-20", 50.0, 8, 8, 3))
            ExerciseDatabase.getInstance(context).WeightedExerciseDao().Insert(
                WeightedExerciseData(
                    2,
                    "Hammer Curl",
                    "16-08-20",
                    52.5,
                    9,
                    7,
                    6
                )
            )
            ExerciseDatabase.getInstance(context).WeightedExerciseDao().Insert(
                WeightedExerciseData(
                    2,
                    "Hammer Curl",
                    "16-08-20",
                    52.5,
                    9,
                    7,
                    9
                )
            )
        }
    }
})*/
