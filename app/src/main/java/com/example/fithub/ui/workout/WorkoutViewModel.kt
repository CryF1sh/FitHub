package com.example.fithub.ui.workout

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.fithub.api.ServiceGenerator
import com.example.fithub.models.WorkoutPlanCreate
import com.example.fithub.models.WorkoutPlanCreateResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkoutViewModel : ViewModel() {
    /*private fun createWorkoutPlan(plan: WorkoutPlanCreate) {
        // Выполняем запрос к вашему API
        val service = ServiceGenerator.workoutService
        service.createWorkoutPlan(plan).enqueue(object : Callback<WorkoutPlanCreate> {
            override fun onResponse(call: Call<WorkoutPlanCreate>, response: Response<WorkoutPlanCreateResponse>) {
                if (response.isSuccessful) {
                    // Получаем созданный план тренировок
                    val createdPlan = response.body()

                    // Сохраняем ID созданного плана в SharedPreferences
                    val sharedPreferences = requireActivity().getSharedPreferences("workout_plans", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    createdPlan?.planId?.let { planId ->
                        editor.putInt("latest_plan_id", planId)
                        editor.apply()
                    }
                } else {
                    // Обработка ошибки при создании плана тренировок
                    Toast.makeText(requireContext(), "Ошибка при создании плана тренировок", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WorkoutPlan>, t: Throwable) {
                // Обработка ошибки при сетевом запросе
                Toast.makeText(requireContext(), "Ошибка сети", Toast.LENGTH_SHORT).show()
            }
        })
    }*/
}