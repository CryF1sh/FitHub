package com.example.fithub.ui.workout

import MyDiaryListRecyclerViewAdapter
import SharedPreferencesManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithub.databinding.FragmentDiaryListBinding
import com.example.fithub.models.DiaryEntry

class DiaryListFragment : Fragment() {

    private lateinit var binding: FragmentDiaryListBinding
    private lateinit var sharedPreferences: SharedPreferencesManager
    private var diaryEntries: MutableList<DiaryEntry> = mutableListOf()
    private lateinit var adapter: MyDiaryListRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryListBinding.inflate(inflater, container, false)
        sharedPreferences = SharedPreferencesManager(requireContext())

        // Загрузка сохраненных записей из SharedPreferences
        val allEntries = sharedPreferences.getAllDiaryEntries()
        diaryEntries.addAll(allEntries.map { DiaryEntry(it.key, it.value as String) })

        // Инициализация RecyclerView и установка адаптера
        adapter = MyDiaryListRecyclerViewAdapter(diaryEntries)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        
        return binding.root
    }

    // Метод для добавления новой записи в дневник
    private fun addDiaryEntry(date: String, title: String) {
        sharedPreferences.saveDiaryEntry(date, title)
        val newEntry = DiaryEntry(date, title)
        diaryEntries.add(newEntry)
        adapter.notifyItemInserted(diaryEntries.size - 1)
    }
}
