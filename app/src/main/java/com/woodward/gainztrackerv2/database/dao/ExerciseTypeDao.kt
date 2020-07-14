package com.woodward.gainztrackerv2.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.woodward.gainztrackerv2.database.entity.ExerciseType

@Dao
interface ExerciseTypeDao{

    /**
     * OnConflict Strat on Abort -> can't have identical exercise types
     *
     * May change though because it is possible to have the exercise in two different categories
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun Insert(exerciseType: ExerciseType?)

    @Update
    suspend fun Update(exerciseType: ExerciseType?)

    @Delete
    suspend fun Delete(exerciseType: ExerciseType?)

    @Query("SELECT * FROM exercise_type_table WHERE Id=:catId ORDER BY Name ASC")
    fun getExerciseTypeList(catId : Int) : LiveData<List<ExerciseType?>>
}