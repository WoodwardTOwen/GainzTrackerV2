package com.woodward.gainztrackerv2.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData

//NEED RE WRITING -> the null acceptance needs re doing
@Dao
interface WeightedExerciseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) //ExerciseDataCanConflict
    suspend fun Insert(exercise: WeightedExerciseData?)

    @Update
    suspend fun Update(exercise: WeightedExerciseData?)

    @Delete
    suspend fun Delete(exercise: WeightedExerciseData?)

    //Delete all exercises for a specific date
    @Query("DElETE FROM weight_exercise_data_table WHERE date = :date")
    suspend fun DeleteAllExercises(date: String?)

    //Select All Exercises From the table
    @Query("SELECT * FROM weight_exercise_data_table")
    suspend fun GetEveryExercise(): LiveData<List<WeightedExerciseData?>>

    //Get all exercises that match the current date
    @Query("SELECT * FROM weight_exercise_data_table WHERE date = :date")
    suspend fun GetAllExercisesByDate(date: String?): LiveData<List<WeightedExerciseData?>>

    //Get all exercises that match the current exerciseName
    @Query("SELECT * FROM weight_exercise_data_table WHERE Name = :exerciseName")
    suspend fun GetSpecificExercisesByName(exerciseName: String?): LiveData<List<WeightedExerciseData?>>
}