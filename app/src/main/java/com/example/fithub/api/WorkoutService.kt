package com.example.fithub.api

import com.example.fithub.models.ExerciseInfo
import com.example.fithub.models.WorkoutPlanCreate
import com.example.fithub.models.WorkoutPlanCreateResponse
import com.example.fithub.models.WorkoutPlanResponse
import com.example.fithub.models.WorkoutPlanUpdate
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface WorkoutService {
    @POST("api/workoutplan")
    fun createWorkoutPlan(@Body plan: WorkoutPlanCreate, @HeaderMap headers: Map<String, String>): Call<Int>
    @GET("api/workoutplan/{id}")
    fun getWorkoutPlan(@Path("id") planId: Int): Call<WorkoutPlanResponse>
    @GET("/api/Workoutplan/{id}/exercises")
    fun getExerciseWorkoutPlan(@Path("id") planId: Int): Call<MutableList<ExerciseInfo>>
    @PUT("api/workoutplan/{id}")
    fun updateWorkoutPlan(@Path("id") planId: Int, @Body plan: WorkoutPlanUpdate, @HeaderMap headers: Map<String, String>): Call<Int>
    @GET("api/search/exercises")
    fun searchExercisesByName(@Query("name") name: String): Call<List<String>>

}