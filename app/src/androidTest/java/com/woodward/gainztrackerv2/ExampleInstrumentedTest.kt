package com.woodward.gainztrackerv2

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.woodward.gainztrackerv2.database.ExerciseDatabase
import com.woodward.gainztrackerv2.database.dao.CategoryDao
import com.woodward.gainztrackerv2.database.dao.ExerciseTypeDao
import com.woodward.gainztrackerv2.database.entity.Category
import com.woodward.gainztrackerv2.database.entity.ExerciseType
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var categoryDao: CategoryDao
    private lateinit var db: ExerciseDatabase
    private lateinit var exerciseDao: ExerciseTypeDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ExerciseDatabase::class.java).build()
        categoryDao = db.CategoryDao()
        exerciseDao = db.ExerciseType()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val categoryx = Category(1, "Bicep")
        val category2 = Category(2, "Tricep")

        val exerciseType = ExerciseType(1, "Test", 1, false)
        val exerciseType2 = ExerciseType(2, "Test2", 2, false)

        /*categoryDao.Insert(categoryx)
        categoryDao.Insert(category2)

        exerciseDao.Insert(exerciseType)
        exerciseDao.Insert(exerciseType2)*/

        val exercises = exerciseDao.getExerciseTypeListTest(2)
        assertThat(exercises[0], equalTo(exerciseType2))
    }
}