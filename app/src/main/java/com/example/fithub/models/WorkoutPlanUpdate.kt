package com.example.fithub.models

data class WorkoutPlanUpdate(
    val name: String,
    val description: String,
    val privacy: Boolean,
    val exercisesInfo: List<ExerciseInfo>
)
