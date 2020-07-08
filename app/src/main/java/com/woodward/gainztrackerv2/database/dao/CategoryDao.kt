package com.woodward.gainztrackerv2.database.dao

import androidx.room.*
import com.woodward.gainztrackerv2.database.entity.Category
import com.woodward.gainztrackerv2.database.entity.ExerciseType

@Dao
interface CategoryDao {
    /**
     * OnConflict Strat on Abort -> can't have identical exercise types
    */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun Insert(category: Category?)

    @Update
    suspend fun Update(category: Category?)

    @Delete
    suspend fun Delete(category: Category?)
}