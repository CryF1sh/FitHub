package com.example.fithub.api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.fithub.R
import com.example.fithub.models.ExerciseInfo

class ExerciseAdapter(val exerciseList: MutableList<ExerciseInfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_EXERCISE = 0
        private const val VIEW_TYPE_BUTTON = 1
    }

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textExerciseName: EditText = itemView.findViewById(R.id.textExerciseName)
        val editTextSets: EditText = itemView.findViewById(R.id.editTextSets)
        val editTextReps: EditText = itemView.findViewById(R.id.editTextReps)
        val editTextWeightLoad: EditText = itemView.findViewById(R.id.editTextWeightLoad)
        val editTextLeadTime: EditText = itemView.findViewById(R.id.editTextLeadTime)
    }

    inner class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addButton: ImageButton = itemView.findViewById(R.id.addExerciseButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_EXERCISE -> {
                val itemView = LayoutInflater.from(parent.context).inflate(
                    R.layout.exercise_item,
                    parent,
                    false
                )
                ExerciseViewHolder(itemView)
            }
            VIEW_TYPE_BUTTON -> {
                val itemView = LayoutInflater.from(parent.context).inflate(
                    R.layout.button_exercise_add_item,
                    parent,
                    false
                )
                ButtonViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_EXERCISE -> {
                val currentItem = exerciseList[position]
                val exerciseHolder = holder as ExerciseViewHolder
                if (currentItem.exerciseInfoId == null) {
                    // Поля оставляются пустыми
                    exerciseHolder.textExerciseName.text = null
                    exerciseHolder.editTextSets.text = null
                    exerciseHolder.editTextReps.text = null
                    exerciseHolder.editTextWeightLoad.text = null
                    exerciseHolder.editTextLeadTime.text = null
                } else {
                    // Поля заполняются данными
                    //exerciseHolder.textExerciseName.setText(currentItem.name)
                    exerciseHolder.editTextSets.setText(currentItem.sets.toString())
                    exerciseHolder.editTextReps.setText(currentItem.reps.toString())
                    exerciseHolder.editTextWeightLoad.setText(currentItem.weightLoad.toString())
                    exerciseHolder.editTextLeadTime.setText(currentItem.leadTime.toString())
                }
            }
            VIEW_TYPE_BUTTON -> {
                val buttonHolder = holder as ButtonViewHolder
                buttonHolder.addButton.setOnClickListener {
                    exerciseList.add(ExerciseInfo(null,null,null,null,null,null,null,null))
                    notifyItemInserted(exerciseList.size)
                }
            }
        }
    }

    override fun getItemCount() = exerciseList.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position < exerciseList.size) {
            VIEW_TYPE_EXERCISE
        } else {
            VIEW_TYPE_BUTTON
        }
    }
    fun getMutableExerciseList(): MutableList<ExerciseInfo> {
        return exerciseList
    }

}
