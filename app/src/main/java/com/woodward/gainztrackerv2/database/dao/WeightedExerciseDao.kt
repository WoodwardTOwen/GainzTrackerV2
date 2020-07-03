package com.woodward.gainztrackerv2.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData

//NEED RE WRITING
@Dao
interface WeightedExerciseDao {
    @Insert
    fun Insert(exercise: WeightedExerciseData?)

    @Update
    fun Update(exercise: WeightedExerciseData?)

    @Delete
    fun Delete(exercise: WeightedExerciseData?)

    //Delete all exercises for a specific date
    @Query("DElETE FROM weight_exercise_data_table WHERE date = :date")
    fun DeleteAllExercises(date: String?)

    //Select All Exercises From the table
    @Query("SELECT * FROM weight_exercise_data_table")
    fun GetEveryExercise(): LiveData<List<WeightedExerciseData?>?>?

    //Get all exercises that match the current date
    @Query("SELECT * FROM weight_exercise_data_table WHERE date = :date")
    fun GetAllExercisesByDate(date: String?): LiveData<List<WeightedExerciseData?>?>?

    //Get all exercises that match the current exerciseName
    @Query("SELECT * FROM weight_exercise_data_table WHERE exercise_name = :exerciseName")
    fun GetSpecificExercisesByName(exerciseName: String?): LiveData<List<WeightedExerciseData?>?>?
}