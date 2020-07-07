package com.woodward.gainztrackerv2.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData

@BindingAdapter("setsFormatted") //Might need re-writing
fun setsFormatted(view: TextView, item: WeightedExerciseData?) {
    item?.let {
        view.setText(R.string.SetsNormal + item.sets)
    }
}

@BindingAdapter("exerciseNameFormatted")
fun exerciseNameFormatted(view: TextView, item: WeightedExerciseData?) {
    item?.let {
        view.text = item.exerciseName
    }
}

@BindingAdapter("weightFormatted")
fun weightFormatted(view: TextView, item: WeightedExerciseData?) {
    item?.let {
        view.text = (item.weight + R.string.Kg).toString()
    }
}

@BindingAdapter("repsFormatted")
fun repsFormatted(view: TextView, item: WeightedExerciseData?) {
    item?.let {
        view.setText(item.reps + R.string.Reps)
    }
}

@BindingAdapter("rpeFormatted")
fun rpeFormatted(view: TextView, item: WeightedExerciseData?) {
    item?.let {
        view.setText(item.rpe + R.string.RPE)
    }
}