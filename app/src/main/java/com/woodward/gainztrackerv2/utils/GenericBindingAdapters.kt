package com.woodward.gainztrackerv2.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData


@BindingAdapter("androidvisibility")
fun View.setVisibility(value: Boolean) {
    this.setVisibility(if (value) View.VISIBLE else View.GONE)
}



