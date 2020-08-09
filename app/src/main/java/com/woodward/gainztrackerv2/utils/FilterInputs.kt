package com.woodward.gainztrackerv2.utils

import android.text.InputFilter
import android.text.Spanned


class FilterInputs internal constructor(min: String, max: String) : InputFilter {
    private var min: Int = 0
    private var max: Int = 10

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            val input = (dest.toString() + source.toString()).toInt()
            if (isInRange(min, max, input)) return null
        } catch (ex: NumberFormatException) {
            ex.message
        }
        return ""
    }

    fun FilterInputs(min: String, max: String) {
        this.min = min.toInt()
        this.max = max.toInt()
    }

    //Checks whether the inputted RPE is in range
    private fun isInRange(a: Int, b: Int, c: Int): Boolean {
        return if (b > a) {
            c in a..b
        } else {
            c in b..a
        }
    }

}