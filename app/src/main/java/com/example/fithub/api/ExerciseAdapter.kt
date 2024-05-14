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
                exerciseHolder.textExerciseName.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        // Ничего не делаем перед изменением текста
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        // Вызываем метод для выполнения запроса на получение списка упражнений
                        fetchExerciseSuggestions(s.toString(), exerciseHolder.textExerciseName.context) { suggestions ->
                            // Обновляем список предложений в автозаполнении
                            val adapter = ArrayAdapter(exerciseHolder.textExerciseName.context, android.R.layout.simple_dropdown_item_1line, suggestions)
                            exerciseHolder.textExerciseName.setAdapter(adapter)
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {
                        // Ничего не делаем после изменения текста
                    }
                })

                if (currentItem.exerciseInfoId == null) {
                    // Поля оставляются пустыми
                    exerciseHolder.textExerciseName.text = null
                    exerciseHolder.editTextSets.text = null
                    exerciseHolder.editTextReps.text = null
                    exerciseHolder.editTextWeightLoad.text = null
                    exerciseHolder.editTextLeadTime.text = null
                } else {
                    // Поля заполняются данными
                    exerciseHolder.textExerciseName.setText(currentItem.name)
                    exerciseHolder.editTextSets.setText(currentItem.sets.toString())
                    exerciseHolder.editTextReps.setText(currentItem.reps.toString())
                    exerciseHolder.editTextWeightLoad.setText(currentItem.weightLoad.toString())
                    exerciseHolder.editTextLeadTime.setText(currentItem.leadTime.toString())
                }
            }
            VIEW_TYPE_BUTTON -> {
                val buttonHolder = holder as ButtonViewHolder
                buttonHolder.addButton.setOnClickListener {
                    exerciseList.add(ExerciseInfo(null,null,null,null,null,null,null,null, null))
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
                Toast.makeText(context, "Ошибка сети", Toast.LENGTH_SHORT).show()
                callback(emptyList())
            }
        })
    }

}
