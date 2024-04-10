package com.example.fithub.models

data class ExerciseInfo(
    val exerciseInfoId: Int,
    val exerciseId: Int,
    val sets: Int,
    val reps: Int,
    val weightLoad: Double,
    val leadTime: Long // Время в миллисекундах
)
