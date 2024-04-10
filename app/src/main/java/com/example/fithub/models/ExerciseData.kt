package com.example.fithub.models

data class ExerciseData(
    val exerciseId: Int,
    val name: String,
    var sets: Int = 0,
    var reps: Int = 0,
    var weightLoad: Double = 0.0,
    var leadTime: Long = 0L
)
