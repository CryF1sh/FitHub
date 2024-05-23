package com.example.fithub.models

data class ExerciseInfo(
    val exerciseInfoId: Int?,
    val exerciseId: Int?,
    val planId: Int?,
    val place: Int?,
    var sets: Int?,
    var reps: Int?,
    var weightLoad: Double?,
    var leadTime: Long?,
    var name: String?
)
