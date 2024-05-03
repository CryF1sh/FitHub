package com.example.fithub.ui.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fithub.R
import com.example.fithub.utils.WorkoutViewModelFactory

class WorkoutPlanEditFragment() : Fragment() {

    private lateinit var workoutViewModel: WorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_workout_plan_edit, container, false)

        // Инициализация ViewModel
        val factory = WorkoutViewModelFactory(requireContext())
        workoutViewModel = ViewModelProvider(this, factory).get(WorkoutViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация RecyclerView и установка LayoutManager
        val recyclerViewExercises = view.findViewById<RecyclerView>(R.id.recyclerViewExercises)
        recyclerViewExercises.layoutManager = LinearLayoutManager(requireContext())

        // TODO: Установка адаптера для RecyclerView
        // recyclerViewExercises.adapter = ваш_адаптер

        // Обработка нажатия кнопки "Сохранить изменения"
        val buttonSaveWorkoutPlan = view.findViewById<Button>(R.id.buttonSaveWorkoutPlan)
        buttonSaveWorkoutPlan.setOnClickListener {
            // TODO: Реализуйте сохранение изменений в плане тренировки
        }
    }
}
