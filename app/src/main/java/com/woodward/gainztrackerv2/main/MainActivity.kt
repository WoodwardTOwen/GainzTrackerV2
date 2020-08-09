package com.woodward.gainztrackerv2.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.woodward.gainztrackerv2.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    /**
     * Makes Controlling the date a lot easier -> global information across the application
     * Removes the need to constantly pass data back and forth between fragments
     */
    object DataHolder {
        private var Date = ""
        fun setDate(s: String) {
            Date = s
        }

        fun getDate(): String {
            return Date
        }
    }
}