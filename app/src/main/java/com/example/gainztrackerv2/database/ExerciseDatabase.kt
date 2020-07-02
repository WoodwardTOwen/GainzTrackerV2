package com.example.gainztrackerv2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Needs entities adding
//@Database(version = 1)
abstract class ExerciseDatabase : RoomDatabase() {


    //Dao Declaration goes here

    companion object {
        @Volatile //Helps ensure data is always up to date, writes and reads from main memory instead of cache
        private var INSTANCE : ExerciseDatabase? = null
        fun getInstance(context : Context) : ExerciseDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext, ExerciseDatabase::class.java, "exercise_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}