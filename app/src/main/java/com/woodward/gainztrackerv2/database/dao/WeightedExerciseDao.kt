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

    /**
     * In The ViewModel before it reaches the repository -> extract ideas from the gathered LiveData
     * This should be called once the entire exercise block is highlighted and attempted to be removed
     * i.e. get data via name and date then extract the Id's, then called method below
     */
    @Query("delete from weight_exercise_data_table where id in (:idList)")
    suspend fun DeleteMultipleData(idList: List<Int>?)

    /**
     * Need a query for the above interaction like the one below: Needs re writing
     * https://stackoverflow.com/questions/55478489/android-room-how-to-delete-multiple-rows-in-the-table-by-id-list/55483157
     *
     * Shown Above
     *
     */

    //Delete all exercises for a specific date
    @Query("DElETE FROM weight_exercise_data_table WHERE date = :date")
    suspend fun DeleteAllExercises(date: String?)

    //Select All Exercises From the table
    @Query("select * from weight_exercise_data_table")
    fun GetEveryExercise(): LiveData<List<WeightedExerciseData?>>

    //Get all exercises that match the current date
    @Query("SELECT * FROM weight_exercise_data_table WHERE date = :date")
    fun GetAllExercisesByDate(date: String?): LiveData<List<WeightedExerciseData?>>

    //Get all exercises that match the current exerciseName
    @Query("SELECT * FROM weight_exercise_data_table WHERE Name = :exerciseName")
    fun GetSpecificExercisesByName(exerciseName: String?): LiveData<List<WeightedExerciseData?>>

    @Query("SELECT * FROM weight_exercise_data_table WHERE Name = :exerciseName AND date = :date ORDER BY Id ASC")
    fun GetDataForDateAndName (exerciseName: String?, date: String?) : LiveData<List<WeightedExerciseData?>>
}