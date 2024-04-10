package com.example.fithub.models

data class WorkoutPlanExercise(
    val exerciseId: Int,
    val name: String,
    val sets: Int,
    val reps: Int,
    val weightLoad: Double,
    val leadTime: Long // Время в миллисекундах
    //val exerciseInfo: ExerciseInfo
)
