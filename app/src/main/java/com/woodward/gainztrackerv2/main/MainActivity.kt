package com.woodward.gainztrackerv2.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.woodward.gainztrackerv2.R
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}