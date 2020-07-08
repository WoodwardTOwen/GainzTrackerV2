package com.woodward.gainztrackerv2.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData


/**
 * For the Main RecyclerView in the Main UI -> Binding adapter for sets
 */

@BindingAdapter("setsFormatted") //Might need re-writing
fun setsFormatted(view: TextView, item: WeightedExerciseData?) {
    item?.let {
        view.setText(R.string.SetsNormal + item.sets)
    }
}

/**
 * For the Main RecyclerView in the Main UI -> Binding adapter for the exercise name
 */

@BindingAdapter("exerciseNameFormatted")
fun exerciseNameFormatted(view: TextView, item: WeightedExerciseData?) {
    item?.let {
        view.text = item.exerciseName
    }
}

/**
 * For the Exercise Details RecyclerView when viewing the exercise detailing (weights) -> Binding adapter for weight (Kg)
 */

@BindingAdapter("weightFormatted")
fun weightFormatted(view: TextView, item: WeightedExerciseData?) {
    item?.let {
        view.text = (item.weight + R.string.Kg).toString()
    }
}

/**
 * For the Exercise Details RecyclerView when viewing the exercise detailing (weights) -> Binding adapter for reps
 */

@BindingAdapter("repsFormatted")
fun repsFormatted(view: TextView, item: WeightedExerciseData?) {
    item?.let {
        view.setText(item.reps + R.string.Reps)
    }
}

/**
 * For the Exercise Details RecyclerView when viewing the exercise detailing (weights) -> Binding adapter for rpe
 */

@BindingAdapter("rpeFormatted")
fun rpeFormatted(view: TextView, item: WeightedExerciseData?) {
    item?.let {
        view.setText(item.rpe + R.string.RPE)
    }
}