package com.woodward.gainztrackerv2.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.woodward.gainztrackerv2.database.entity.Category


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

    @Query("SELECT * FROM Category_Table ORDER BY Name ASC")
    fun getAllCategories(): LiveData<List<Category?>>

    @Query("SELECT * FROM Category_Table ORDER BY Name ASC")
    fun getAllCategoriesTest(): List<Category?>

    /**
     * Checks to see if [name] exists with the category table
     * Alerts the user whether they can add the category or not
     */
    @Query("SELECT EXISTS(SELECT * FROM category_table WHERE Name = :name)")
    suspend fun checkIfCategoryExists(name : String) : Boolean
}