package com.woodward.gainztrackerv2.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.database.entity.Category
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData


/**
 * For the Main RecyclerView in the Main UI -> Binding adapter for sets
 */

@BindingAdapter("setsFormatted") //Might need re-writing
fun TextView.setsFormatted(item: Int?) {
    item?.let {
        this.text = "${this.context.getString(R.string.Sets)} ${item}"
    }
}
/**
 * For the Main RecyclerView in the Main UI -> Binding adapter for the exercise name
 */

@BindingAdapter("exerciseNameFormatted")
fun TextView.exerciseNameFormatted(item: String?) {
    item?.let {
        this.text = if (item.isNullOrEmpty()) "" else "$item"
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

@BindingAdapter("categoriesFormatted")
fun TextView.categories(item: String?) {
    item?.let{
        this.text = if (item.isNullOrEmpty()) "" else "$item"
    }
}