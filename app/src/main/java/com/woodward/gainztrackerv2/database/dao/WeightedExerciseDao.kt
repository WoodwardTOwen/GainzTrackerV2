package com.woodward.gainztrackerv2.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData

//NEED RE WRITING -> the null acceptance needs re doing
@Dao
interface WeightedExerciseDao {

    //Test
    @Insert(onConflict = OnConflictStrategy.IGNORE) //ExerciseDataCanConflict
    suspend fun Insert(exercise: WeightedExerciseData?)

    @Update
    suspend fun Update(exercise: WeightedExerciseData?)

    /**
     * Update certain fields of table for sets -> in conjunction with date and exercise name
     * Should be called immediately after an insert function has been committed
     * Insert New Information then UPDATE remaining fields in conjunction with exerciseName and Date
     *
     */

    @Query("UPDATE weight_exercise_data_table SET sets = :pSets WHERE Id in (:idList)")
    suspend fun UpdateSetsExercise(idList: List<Int?>, pSets: Int?)

    @Delete
    suspend fun Delete(exercise: WeightedExerciseData?)

    /**
     * In The ViewModel before it reaches the repository -> extract id's from the gathered LiveData
     * This should be called once the entire exercise block is highlighted and attempted to be removed
     * i.e. get data via name and date then extract the Id's, then called method below
     */

    @Query("delete from weight_exercise_data_table where Id in (:idList)")
    suspend fun DeleteMultipleDataViaID(idList : List<Int?>)

    /**
     * Similar to query above however tries a different approach to be attempted for deleting data
     */

    @Query("delete from weight_exercise_data_table where Name = :exerciseName and Date = :date")
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

    //Select All Exercises From the table
    @Query("select * from weight_exercise_data_table")
    fun GetEveryExercise(): LiveData<List<WeightedExerciseData?>>

    /**
     * Gets exercise name and sets based on date
     * Again -> maybe incorrect format -> Should only get one record
     */
    @Query("SELECT DISTINCT * FROM weight_exercise_data_table WHERE date = :date")
    fun GetNameAndSetsForDate(date: String?): LiveData<List<WeightedExerciseData?>>

    /*@Query("SELECT DISTINCT Name, sets FROM weight_exercise_data_table WHERE date = :date")
    fun GetNameSetsForDatePlaceHolder(date: String?): LiveData<List<ExerciseSetPlaceHolderClass?>>*/

    //Get all exercises that match the current date
    @Query("SELECT * FROM weight_exercise_data_table WHERE date = :date")
    fun GetAllExercisesByDate(date: String?): LiveData<List<WeightedExerciseData?>>

    //Get all exercises that match the current exerciseName
    @Query("SELECT * FROM weight_exercise_data_table WHERE Name = :exerciseName")
    fun GetSpecificExercisesByName(exerciseName: String?): LiveData<List<WeightedExerciseData?>>

    /**
     * This will be used for exerciseDetails recyclerview -> gets the only information relevant to a certain exercise
     */

    @Query("SELECT * FROM weight_exercise_data_table WHERE Name = :exerciseName AND date = :date ORDER BY Id ASC")
    fun GetDataForDateAndName (exerciseName: String?, date: String?) : LiveData<List<WeightedExerciseData?>>
}