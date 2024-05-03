package com.example.fithub.api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fithub.R
import com.example.fithub.models.WorkoutPlanExercise

class ExerciseAdapter(private val exerciseList: List<WorkoutPlanExercise>) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textExerciseName: EditText = itemView.findViewById(R.id.textExerciseName)
        val editTextSets: EditText = itemView.findViewById(R.id.editTextSets)
        val editTextReps: EditText = itemView.findViewById(R.id.editTextReps)
        val editTextWeightLoad: EditText = itemView.findViewById(R.id.editTextWeightLoad)
        val editTextLeadTime: EditText = itemView.findViewById(R.id.editTextLeadTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.exercise_item,
            parent,
            false
        )
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentItem = exerciseList[position]
        holder.textExerciseName.setText(currentItem.name.toString())
        holder.editTextSets.setText(currentItem.sets.toString())
        holder.editTextReps.setText(currentItem.reps.toString())
        holder.editTextWeightLoad.setText(currentItem.weightLoad.toString())
        holder.editTextLeadTime.setText(currentItem.leadTime.toString())
    }

    override fun getItemCount() = exerciseList.size
}
