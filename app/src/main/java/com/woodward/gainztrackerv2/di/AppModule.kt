package com.woodward.gainztrackerv2.di

import android.content.Context
import androidx.room.Room
import com.woodward.gainztrackerv2.database.ExerciseDatabase
import com.woodward.gainztrackerv2.database.dao.CategoryDao
import com.woodward.gainztrackerv2.database.dao.ExerciseTypeDao
import com.woodward.gainztrackerv2.database.dao.WeightedExerciseDao
import com.woodward.gainztrackerv2.repositories.CategoryRepository
import com.woodward.gainztrackerv2.repositories.ExerciseRepository
import com.woodward.gainztrackerv2.repositories.ExerciseTypeRepository
import com.woodward.gainztrackerv2.utils.Constants.ROOM_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * tells compiler to install this module inside the application component class
 * All the dependencies will be created in the onCreate of the application's life -> destroyed once the application is destroyed
 */
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideExerciseDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        ExerciseDatabase::class.java,
        ROOM_DATABASE_NAME
    ).build()


    @Singleton
    @Provides
    fun provideWeightExerciseDao(db: ExerciseDatabase) = db.WeightedExerciseDao()

    @Singleton
    @Provides
    fun provideCategoryDao(db: ExerciseDatabase) = db.CategoryDao()

    @Singleton
    @Provides
    fun provideExerciseTypeDao(db: ExerciseDatabase) = db.ExerciseType()

}