package com.example.fithub.utils
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fithub.ui.workout.WorkoutViewModel

class WorkoutViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutViewModel::class.java)) {
            return WorkoutViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
