package com.example.fithub.api

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.fithub.R
import com.example.fithub.models.ExerciseInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class ExerciseAdapter(val exerciseList: MutableList<ExerciseInfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_EXERCISE = 0
        private const val VIEW_TYPE_BUTTON = 1
    }

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textExerciseName: AutoCompleteTextView = itemView.findViewById(R.id.textExerciseName)
        val editTextSets: EditText = itemView.findViewById(R.id.editTextSets)
        val editTextReps: EditText = itemView.findViewById(R.id.editTextReps)
        val editTextWeightLoad: EditText = itemView.findViewById(R.id.editTextWeightLoad)
        val editTextLeadTime: EditText = itemView.findViewById(R.id.editTextLeadTime)
        val deleteButton: ImageButton = itemView.findViewById(R.id.exerciseDeleteButton)

        private var isInitializing = false

        fun bind(exerciseInfo: ExerciseInfo) {
            isInitializing = true

            textExerciseName.setText(exerciseInfo.name)
            editTextSets.setText(exerciseInfo.sets?.toString())
            editTextReps.setText(exerciseInfo.reps?.toString())
            editTextWeightLoad.setText(exerciseInfo.weightLoad?.toString())
            editTextLeadTime.setText(exerciseInfo.leadTime?.toString())

            isInitializing = false

            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    exerciseList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, exerciseList.size)
                }
            }
        }

        fun addTextWatchers(exerciseInfo: ExerciseInfo) {
            textExerciseName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (!isInitializing) {
                        fetchExerciseSuggestions(s.toString(), textExerciseName.context) { suggestions ->
                            val adapter = ArrayAdapter(textExerciseName.context, android.R.layout.simple_dropdown_item_1line, suggestions)
                            textExerciseName.setAdapter(adapter)
                        }
                        exerciseInfo.name = s.toString()
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            editTextSets.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (!isInitializing) {
                        exerciseInfo.sets = s.toString().toIntOrNull()
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            editTextReps.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (!isInitializing) {
                        exerciseInfo.reps = s.toString().toIntOrNull()
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            editTextWeightLoad.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (!isInitializing) {
                        exerciseInfo.weightLoad = s.toString().toDoubleOrNull()
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            editTextLeadTime.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (!isInitializing) {
                        exerciseInfo.leadTime = s.toString().toLongOrNull()//.toTimeSpan()
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }
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
                exerciseHolder.addTextWatchers(currentItem)
                exerciseHolder.bind(currentItem)
            }
            VIEW_TYPE_BUTTON -> {
                val buttonHolder = holder as ButtonViewHolder
                buttonHolder.addButton.setOnClickListener {
                    exerciseList.add(ExerciseInfo(null, null, null, null, null, null, null, null, null))
                    notifyItemInserted(exerciseList.size)
                }
            }
        }
    }

    fun Long?.toTimeSpan(): Long? {
        return this?.let {
            it * 60 * 1000 // Преобразование минут в миллисекунды
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
    fun fetchExerciseSuggestions(query: String, context: Context, callback: (List<String>) -> Unit) {
        val service = ServiceGenerator.workoutService

        service.searchExercisesByName(query).enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val suggestions = response.body() ?: emptyList()
                    callback(suggestions)
                } else {
                    callback(emptyList())
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Toast.makeText(context, "Ошибка сети: поиск упражнений", Toast.LENGTH_SHORT).show()
                callback(emptyList())
            }
        })
    }

}
