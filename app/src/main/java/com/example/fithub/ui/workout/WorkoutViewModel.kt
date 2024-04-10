package com.example.fithub.ui.workout

import SharedPreferencesManager
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.fithub.api.ServiceGenerator
import com.example.fithub.models.WorkoutPlanCreate
import com.example.fithub.models.WorkoutPlanCreateResponse
import com.example.fithub.models.WorkoutPlanResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkoutViewModel(private val context: Context) : ViewModel() {

    fun createWorkoutPlan(name: String, description: String, isPrivate: Boolean, jwtToken: String?, callback: (Int?) -> Unit) {
        val newWorkoutPlan = WorkoutPlanCreate(name, description, isPrivate)

        val service = ServiceGenerator.workoutService

        val headers = HashMap<String, String>()
        jwtToken?.let { token ->
            headers["Authorization"] = "Bearer $token"
        }

        service.createWorkoutPlan(newWorkoutPlan, headers).enqueue(object : Callback<WorkoutPlanCreateResponse> {
            override fun onResponse(call: Call<WorkoutPlanCreateResponse>, response: Response<WorkoutPlanCreateResponse>) {
                if (response.isSuccessful) {
                    val createdPlanId = response.body()?.id

                    // Сохраняем ID созданного плана в SharedPreferences
                    createdPlanId?.let { planId ->
                        val sharedPreferencesManager = SharedPreferencesManager(context)
                        sharedPreferencesManager.saveLatestWorkoutPlanId(planId)
                        callback(planId)
                    }
                } else {
                    // Обработка ошибки при создании плана тренировок
                    Toast.makeText(context, "Ошибка при создании плана тренировок", Toast.LENGTH_SHORT).show()
                    callback(null)
                }
            }

            override fun onFailure(call: Call<WorkoutPlanCreateResponse>, t: Throwable) {
                // Обработка ошибки при сетевом запросе
                Toast.makeText(context, "Ошибка сети", Toast.LENGTH_SHORT).show()
                callback(null)
            }
        })
    }
    fun getWorkoutPlan(planId: Int, callback: (WorkoutPlanResponse?) -> Unit) {
        val service = ServiceGenerator.workoutService

        service.getWorkoutPlan(planId).enqueue(object : Callback<WorkoutPlanResponse> {
            override fun onResponse(call: Call<WorkoutPlanResponse>, response: Response<WorkoutPlanResponse>) {
                if (response.isSuccessful) {
                    val workoutPlan = response.body()
                    callback(workoutPlan)
                } else {
                    Toast.makeText(context, "Ошибка при получении плана тренировки", Toast.LENGTH_SHORT).show()
                    callback(null)
                }
            }

            override fun onFailure(call: Call<WorkoutPlanResponse>, t: Throwable) {
                Toast.makeText(context, "Ошибка сети", Toast.LENGTH_SHORT).show()
                callback(null)
            }
        })
    }

}
