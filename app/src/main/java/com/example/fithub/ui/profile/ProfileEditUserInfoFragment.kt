package com.example.fithub.ui.profile

import SharedPreferencesManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fithub.R
import com.example.fithub.api.ServiceGenerator
import com.example.fithub.models.UserProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileEditUserInfoFragment : Fragment() {
    private var _binding: View? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    private lateinit var editFirstName: EditText
    private lateinit var editLastName: EditText
    private lateinit var editCity: EditText
    private lateinit var editAboutMe: EditText
    private lateinit var saveChangesButton: Button

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPreferencesManager = SharedPreferencesManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflater.inflate(R.layout.fragment_profile_edit_user_info, container, false)

        editFirstName = binding.findViewById(R.id.edit_first_name)
        editLastName = binding.findViewById(R.id.edit_last_name)
        editCity = binding.findViewById(R.id.edit_city)
        editAboutMe = binding.findViewById(R.id.edit_about_me)
        saveChangesButton = binding.findViewById(R.id.save_changes_button)

        saveChangesButton.setOnClickListener {
            saveChanges()
        }

        return binding
    }

    private fun saveChanges() {
        val firstName = editFirstName.text.toString().trim()
        val lastName = editLastName.text.toString().trim()
        val location = editCity.text.toString().trim()
        val bio = editAboutMe.text.toString().trim()

        val userProfile = UserProfile(
            firstname = firstName,
            lastname = lastName,
            location = location,
            bio = bio,
            birthdate = null,
            registrationdate = null
        )

        val jwtToken = sharedPreferencesManager.getAuthToken()
        val service = ServiceGenerator.authService

        val headers = HashMap<String, String>()
        jwtToken?.let { token ->
            headers["Authorization"] = "Bearer $token"
        }

        service.updateProfile(userProfile, headers).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    findNavController().navigateUp()
                    Toast.makeText(context, "Информация была обновлена", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Ошибка при обновлении профиля", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(context, "Ошибка сети", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
