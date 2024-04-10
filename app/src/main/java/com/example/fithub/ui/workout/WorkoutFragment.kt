package com.example.fithub.ui.workout

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.fithub.R
import androidx.navigation.fragment.findNavController
import com.example.fithub.utils.WorkoutViewModelFactory

class WorkoutFragment : Fragment() {

    companion object {
        fun newInstance() = WorkoutFragment()
    }

    private lateinit var viewModel: WorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_workout, container, false)

//        val planButton = view.findViewById<CardView>(R.id.planButton)
//        val diaryButton = view.findViewById<CardView>(R.id.diaryButton)
//
//        planButton.setOnClickListener {
//            val action = WorkoutFragmentDirections.actionNavigationWorkoutToWorkoutPlansFragment()
//            findNavController().navigate(action)
//        }
//
//        diaryButton.setOnClickListener {
//            val action = WorkoutFragmentDirections.actionNavigationWorkoutToDiaryFragment()
//            findNavController().navigate(action)
//        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val planButton = view.findViewById<CardView>(R.id.planButton)
        val diaryButton = view.findViewById<CardView>(R.id.diaryButton)
        val navController = findNavController()

        planButton.setOnClickListener {
            //val action = WorkoutFragmentDirections.actionNavigationWorkoutToWorkoutPlansFragment()
            //navController.navigate(action)
            navController.navigate(R.id.workoutPlansFragment)
        }

        diaryButton.setOnClickListener {
//            val action = WorkoutFragmentDirections.actionNavigationWorkoutToDiaryFragment()
//            navController.navigate(action)
            navController.navigate(R.id.diaryListFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = WorkoutViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, factory).get(WorkoutViewModel::class.java)

    }

}