package com.woodward.gainztrackerv2.di

import androidx.arch.core.util.Function
import com.woodward.gainztrackerv2.database.entity.ExerciseType
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData
import com.woodward.gainztrackerv2.exercisedetails.weights.ExerciseDetailsAdapterListener
import com.woodward.gainztrackerv2.exercisedetails.weights.ExerciseDetailsAdapterWeights
import com.woodward.gainztrackerv2.exerciseselection.categoryselection.CategoryAdapter
import com.woodward.gainztrackerv2.exerciseselection.categoryselection.CategoryAdapterListener
import com.woodward.gainztrackerv2.exerciseselection.exercisetypeselection.ExerciseTypeAdapter
import com.woodward.gainztrackerv2.exerciseselection.exercisetypeselection.ExerciseTypeAdapterListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object AdapterModule {
    /**
     * Category Adapter
     */
    @Provides
    fun getIntArgLambda(): (Int) -> Unit = {}

    @Provides
    fun getCategoryTypeAdapterListener(intLambda: Function1<@JvmSuppressWildcards Int, Unit>) = CategoryAdapterListener(intLambda)

    @Provides
    fun getCategoryTypeAdapter(listener: CategoryAdapterListener) = CategoryAdapter(listener)

    /**
     * ExerciseType Adapter
     */

    @Provides
    fun getExerciseTypeArgLambda() : (ExerciseType) -> Unit ={}

    @Provides
    fun getExerciseTypeListener(exerciseLambda: Function1<@JvmSuppressWildcards ExerciseType, Unit>) = ExerciseTypeAdapterListener(exerciseLambda)

    @Provides
    fun getExerciseTypeAdapter(listener: ExerciseTypeAdapterListener) = ExerciseTypeAdapter(listener)

    /**
     * ExerciseDetails Adapter
     */

    @Provides
    fun getWeightExerciseDataArgLambda() : (WeightedExerciseData) -> Unit={}

    @Provides
    fun getWeightExerciseDataListener(exerciseLambda: Function1<@JvmSuppressWildcards WeightedExerciseData, Unit>) = ExerciseDetailsAdapterListener(exerciseLambda)

    @Provides
    fun getExerciseDetailsAdapter(listener: ExerciseDetailsAdapterListener) = ExerciseDetailsAdapterWeights(listener)
}