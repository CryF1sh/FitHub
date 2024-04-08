package com.example.fithub.api

import com.example.fithub.models.WorkoutPlanCreate
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

interface WorkoutService {
    @POST("api/workoutplan")
    fun createWorkoutPlan(@Body plan: WorkoutPlanCreate): Call<WorkoutPlanCreate>
}