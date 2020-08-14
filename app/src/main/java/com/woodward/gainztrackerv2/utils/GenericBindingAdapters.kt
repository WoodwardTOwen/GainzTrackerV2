package com.woodward.gainztrackerv2.utils

import android.view.View
import androidx.databinding.BindingAdapter


@BindingAdapter("androidvisibility")
fun View.setVisibility(value: Boolean) {
    this.setVisibility(if (value) View.VISIBLE else View.GONE)
}



