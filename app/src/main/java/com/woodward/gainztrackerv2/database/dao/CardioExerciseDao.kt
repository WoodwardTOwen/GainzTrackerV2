package com.woodward.gainztrackerv2.database.dao

import androidx.room.*
import com.woodward.gainztrackerv2.database.entity.CardiovascularExerciseData

@Dao
interface CardioExerciseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCardioExercise(cardio : CardiovascularExerciseData)

    @Update
    suspend fun updateCardioExercise(cardio: CardiovascularExerciseData)

    @Delete
    suspend fun deleteCardioExercise(cardio: CardiovascularExerciseData)

    @Query("SELECT sets FROM cardio_exercise_data_table WHERE date = :date")
    suspend fun getAllCardioSetDataForDate(date: String?) : Int
}