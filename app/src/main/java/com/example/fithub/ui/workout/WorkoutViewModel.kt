package com.example.fithub.ui.workout

import SharedPreferencesManager
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.fithub.api.ServiceGenerator
import com.example.fithub.models.ExerciseInfo
import com.example.fithub.models.WorkoutPlanCreate
import com.example.fithub.models.WorkoutPlanCreateResponse
import com.example.fithub.models.WorkoutPlanResponse
import com.example.fithub.models.WorkoutPlanUpdate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkoutViewModel(private val context: Context) : ViewModel() {

    fun createWorkoutPlan(name: String, description: String, privacy: Boolean, exercisesInfo: List<ExerciseInfo>, jwtToken: String?, callback: (Int?) -> Unit) {
        val newWorkoutPlan = WorkoutPlanCreate(name, description, privacy, exercisesInfo)

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
                        sharedPreferencesManager.saveLatestWorkoutPlanId(planId, newWorkoutPlan.name)
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
    fun updateWorkoutPlan(planId: Int, name: String, description: String, privacy: Boolean, exercisesInfo: List<ExerciseInfo>, jwtToken: String?, callback: (Boolean) -> Unit) {
        val updatedWorkoutPlan = WorkoutPlanUpdate(planId, name, description, privacy, exercisesInfo)

        val service = ServiceGenerator.workoutService

        val headers = HashMap<String, String>()
        jwtToken?.let { token ->
            headers["Authorization"] = "Bearer $token"
        }

        service.updateWorkoutPlan(planId, updatedWorkoutPlan).enqueue(object : Callback<WorkoutPlanCreateResponse> {
            override fun onResponse(call: Call<WorkoutPlanCreateResponse>, response: Response<WorkoutPlanCreateResponse>) {
                if (response.isSuccessful) {
                    // Обновление плана тренировки успешно
                    callback(true)
                } else {
                    Toast.makeText(context, "Ошибка при обновлении плана тренировок", Toast.LENGTH_SHORT).show()
                    callback(false)
                }
            }

            override fun onFailure(call: Call<WorkoutPlanCreateResponse>, t: Throwable) {
                Toast.makeText(context, "Ошибка сети", Toast.LENGTH_SHORT).show()
                callback(false)
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

    fun getExerciseWorkoutPlan(planId: Int, callback: (MutableList<ExerciseInfo>?) -> Unit) {
        val service = ServiceGenerator.workoutService

        service.getExerciseWorkoutPlan(planId).enqueue(object : Callback<MutableList<ExerciseInfo>> {
            override fun onResponse(call: Call<MutableList<ExerciseInfo>>, response: Response<MutableList<ExerciseInfo>>) {
                if (response.isSuccessful) {
                    val exercises = response.body()
                    callback(exercises)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<MutableList<ExerciseInfo>>, t: Throwable) {
                Toast.makeText(context, "Ошибка сети", Toast.LENGTH_SHORT).show()
                callback(null)
            }
        })
    }
}
