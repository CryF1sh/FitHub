package com.example.fithub.ui.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fithub.R
import com.example.fithub.api.ExerciseAdapter
import com.example.fithub.api.PostAdapter
import com.example.fithub.utils.WorkoutViewModelFactory

class WorkoutPlanEditFragment() : Fragment() {

    private lateinit var workoutViewModel: WorkoutViewModel
    private var planId: Int = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_workout_plan_edit, container, false)
        val factory = WorkoutViewModelFactory(requireContext())
        workoutViewModel = ViewModelProvider(this, factory).get(WorkoutViewModel::class.java)
        arguments?.let {
            val safeArgs = WorkoutPlanEditFragmentArgs.fromBundle(it)
            planId = safeArgs.planId
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewExercises = view.findViewById<RecyclerView>(R.id.recyclerViewExercises)
        recyclerViewExercises.layoutManager = LinearLayoutManager(requireContext())

        if (planId != -1) {
            workoutViewModel.getWorkoutPlan(planId) { workoutPlan ->
                if (workoutPlan != null) {
                    // План тренировки получен, установить информацию о плане тренировок

                    view.findViewById<EditText>(R.id.textEditName).setText(workoutPlan.name)
                    view.findViewById<EditText>(R.id.textEditDescription).setText(workoutPlan.description)
                    view.findViewById<Switch>(R.id.switchPrivacy).isChecked = workoutPlan.privacy

                    // Загрузить список упражений плана тренировок и обновите данные в адаптере и установить

                    workoutViewModel.getExerciseWorkoutPlan(planId) {exerciseInfo ->
                        if (exerciseInfo != null){
                            // Получен список упражнений
                            val exerciseAdapter = ExerciseAdapter(exerciseInfo)
                            recyclerViewExercises.adapter = exerciseAdapter
                        }
                        else {
                            // Упражнения не найдены
                            val exerciseAdapter = ExerciseAdapter(arrayListOf())
                            recyclerViewExercises.adapter = exerciseAdapter
                        }
                    }
                } else {
                    // План тренировки не удалось получить
                }
            }
        }
        else {
            // Плана тренировок не существует или создан новый план тренировок
            val exerciseAdapter = ExerciseAdapter(arrayListOf())
            recyclerViewExercises.adapter = exerciseAdapter
        }

        val buttonSaveWorkoutPlan = view.findViewById<Button>(R.id.buttonSaveWorkoutPlan)
        buttonSaveWorkoutPlan.setOnClickListener {

            val name = view.findViewById<EditText>(R.id.textEditName).text.toString()
            val description = view.findViewById<EditText>(R.id.textEditDescription).text.toString()
            val privacy = view.findViewById<Switch>(R.id.switchPrivacy).isChecked

            val exerciseAdapter = recyclerViewExercises.adapter as? ExerciseAdapter
            val exercisesInfo = exerciseAdapter?.getMutableExerciseList() ?: arrayListOf()

            if(planId == -1) {
                // Создание нового плана тренировки
                workoutViewModel.createWorkoutPlan(name, description, privacy, exercisesInfo, null) { createdPlanId ->
                    if (createdPlanId != null) {
                        // Успешно создан новый план тренировки
                    } else {
                        // Не удалось создать план тренировки
                        Toast.makeText(context, "Не удалось создать план тренировки", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                // Обновить план тренировки
                workoutViewModel.updateWorkoutPlan(planId, name, description, privacy, exercisesInfo, null) { success ->
                    if (success) {
                        // Успешно обновлен план тренировки
                    } else {
                        // Не удалось обновить план тренировки
                        Toast.makeText(context, "Не удалось обновить план тренировки", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
