package com.woodward.gainztrackerv2.repository

import kotlinx.coroutines.CompletableJob

//Object to declare it as a singleton
object ExerciseRepository {

    /**
     * Initialise Jobs for Coroutines
     */
    var job: CompletableJob? = null





    fun CancelJobs() {
        job?.cancel()
    }
}