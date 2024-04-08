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

        val planButton = view.findViewById<CardView>(R.id.planButton)
        val diaryButton = view.findViewById<CardView>(R.id.diaryButton)

        planButton.setOnClickListener {
            val action = WorkoutFragmentDirections.actionNavigationWorkoutToWorkoutPlansFragment()
            findNavController().navigate(action)
        }

        diaryButton.setOnClickListener {
            val action = WorkoutFragmentDirections.actionNavigationWorkoutToDiaryFragment()
            findNavController().navigate(action)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

    }

}