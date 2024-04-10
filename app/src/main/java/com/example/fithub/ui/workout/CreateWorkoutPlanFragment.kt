package com.example.fithub.ui.workout

import SharedPreferencesManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fithub.R
import com.example.fithub.utils.WorkoutViewModelFactory

class CreateWorkoutPlanFragment : Fragment() {
    private lateinit var WorkoutViewModel: WorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_workout_plan, container, false)

        val factory = WorkoutViewModelFactory(requireContext())
        WorkoutViewModel = ViewModelProvider(this, factory).get(WorkoutViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameEditText = view.findViewById<AppCompatEditText>(R.id.editTextName)
        val descriptionEditText = view.findViewById<AppCompatEditText>(R.id.editTextDescription)
        val switchPrivacy = view.findViewById<Switch>(R.id.switchPrivacy)
        val continueButton = view.findViewById<Button>(R.id.buttonContinue)

        WorkoutViewModel = ViewModelProvider(this, WorkoutViewModelFactory(requireContext())).get(WorkoutViewModel::class.java)

        continueButton.setOnClickListener {
            val sharedPreferencesManager = SharedPreferencesManager(requireContext())
            val jwtToken = sharedPreferencesManager.getAuthToken()
            WorkoutViewModel.createWorkoutPlan(nameEditText.toString(), descriptionEditText.toString(), switchPrivacy.isChecked, jwtToken) { planId ->
                if (planId != null) {
                    val bundle = Bundle()
                    bundle.putInt("ID", planId);
                    findNavController().navigate(R.id.WorkoutPlanEditFragment, bundle)
                } else {
                    // Обработка ошибки при создании плана тренировки
                }
            }

        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = WorkoutViewModelFactory(requireContext())
        WorkoutViewModel = ViewModelProvider(this, factory).get(com.example.fithub.ui.workout.WorkoutViewModel::class.java)

    }

}
