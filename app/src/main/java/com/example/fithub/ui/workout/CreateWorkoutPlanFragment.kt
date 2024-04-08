package com.example.fithub.ui.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fithub.R
import com.example.fithub.models.WorkoutPlanCreate
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText

class CreateWorkoutPlanFragment : Fragment() {

    private lateinit var nameEditText: TextInputEditText
    private lateinit var descriptionEditText: TextInputEditText
    private lateinit var switchPrivacy: SwitchMaterial
    private lateinit var continueButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_workout_plan, container, false)

        nameEditText = view.findViewById(R.id.editTextName)
        descriptionEditText = view.findViewById(R.id.editTextDescription)
        switchPrivacy = view.findViewById(R.id.switchPrivacy)
        continueButton = view.findViewById(R.id.buttonContinue)

        continueButton.setOnClickListener {
            val action = WorkoutPlanFragmentDirections.actionNavigationWorkoutPlansFragmentToCreate()
            findNavController().navigate(action)
            //createWorkoutPlan()
        }

        return view
    }

    private fun createWorkoutPlan() {
        val name = nameEditText.text.toString().trim()
        val description = descriptionEditText.text.toString().trim()
        val isPrivate = switchPrivacy.isChecked

        val newWorkoutPlan = WorkoutPlanCreate(name, description, isPrivate)

        //createWorkoutPlan(newWorkoutPlan)
    }
}
