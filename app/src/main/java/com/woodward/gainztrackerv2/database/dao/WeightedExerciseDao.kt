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

    /**
     * Returns current amount of sets value for certain exercise name + date
     */

    @Query("SELECT DISTINCT sets FROM weight_exercise_data_table WHERE Name = :exerciseName AND date = :date")
    suspend fun getCurrentAmountOfSets(exerciseName: String?, date: String?) : Int

    @Query("SELECT sets FROM weight_exercise_data_table WHERE date = :date")
    fun getAllWeightSetDataForDate(date: String?) : LiveData<Int?>

    /**
     * Updating all the set values when a new piece of data is added
     */

    @Query("UPDATE weight_exercise_data_table SET sets = :sets WHERE Name = :exerciseName AND date = :date")
    suspend fun updateSetsForExercises(sets: Int, exerciseName: String?, date: String?)


    @Delete
    suspend fun Delete(exercise: WeightedExerciseData?)

    /**
     * Similar to query above however tries a different approach to be attempted for deleting data
     */

    @Query("DELETE FROM weight_exercise_data_table WHERE Name = :exerciseName AND Date = :date")
    suspend fun DeleteMultipleData(exerciseName: String, date: String)

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

    /**
     * Gets exercise name and sets based on date
     */

    @Query("SELECT weight_exercise_data_table.* FROM weight_exercise_data_table WHERE date = :date GROUP BY weight_exercise_data_table.Name")
    fun GetNameAndSetsForDate(date: String?): LiveData<List<WeightedExerciseData>>

    /**
     * This will be used for exerciseDetails recyclerview -> gets the only information relevant to a certain exercise
     */

    @Query("SELECT * FROM weight_exercise_data_table WHERE Name = :exerciseName AND date = :date ORDER BY Id ASC")
    fun GetDataForDateAndName (exerciseName: String?, date: String?) : LiveData<List<WeightedExerciseData?>>
}