package com.example.fithub.ui.workout

import MyWorkoutPlanRecyclerViewAdapter
import SharedPreferencesManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithub.R
import com.example.fithub.models.WorkoutPlanListItem
import com.example.fithub.databinding.FragmentWorkoutplanListBinding

class WorkoutPlanFragment : Fragment() {

    private lateinit var binding: FragmentWorkoutplanListBinding
    private lateinit var sharedPreferences: SharedPreferencesManager
    private var workoutPlans: MutableList<WorkoutPlanListItem> = mutableListOf()
    private lateinit var adapter: MyWorkoutPlanRecyclerViewAdapter
    private lateinit var createButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_workoutplan_list, container, false)
        binding = FragmentWorkoutplanListBinding.inflate(inflater, container, false)
        sharedPreferences = SharedPreferencesManager(requireContext())

        createButton = view.findViewById(R.id.createPlanButton)
        createButton.setOnClickListener {
            val action = WorkoutPlanFragmentDirections.actionNavigationWorkoutPlansFragmentToCreate()
            findNavController().navigate(action)
            //createWorkoutPlan()
        }
        // Загрузка сохраненных планов тренировок из SharedPreferences


        // Инициализация RecyclerView и установка адаптера
        adapter = MyWorkoutPlanRecyclerViewAdapter(workoutPlans)
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter

        return binding.root
    }

    // Метод для добавления нового плана тренировки
    private fun addWorkoutPlan(name: String, creator: String) {

    }
}
