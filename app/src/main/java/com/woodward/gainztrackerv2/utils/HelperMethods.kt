package com.woodward.gainztrackerv2.utils

fun isNullOrWhiteSpace(x: String?): Boolean {
    return x.isNullOrBlank()
}

fun isNumericOrNullDouble(x: Double?) : Boolean {
    return x?.isNaN() ?: true
}

fun isNullInt(x : Int?) : Boolean {
    return x == null
}