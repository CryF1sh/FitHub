package com.example.fithub.ui.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fithub.R
import com.example.fithub.api.ExerciseAdapter

class WorkoutPlanEditFragment() : Fragment() {

    private var planId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            planId = it.getInt("ID", -1)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_workout_plan_edit, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val nameTextView = view.findViewById<TextView>(R.id.textViewName)
//        val descriptionTextView = view.findViewById<TextView>(R.id.)

        val workoutViewModel = WorkoutViewModel(requireContext())

        workoutViewModel.getWorkoutPlan(planId) { workoutPlan ->
            workoutPlan?.let {
                //nameTextView.text = it.name

                val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewExercises)
                val adapter = ExerciseAdapter(workoutPlan.workoutPlanExercises)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter

            }
        }
    }

}