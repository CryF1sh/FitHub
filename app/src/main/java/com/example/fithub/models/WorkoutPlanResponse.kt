package com.example.fithub.models

data class WorkoutPlanResponse(
    val planId: Int,
    val name: String,
    val description: String,
    val privacy: Boolean,
    val workoutPlanExercises: List<WorkoutPlanExercise>
)
