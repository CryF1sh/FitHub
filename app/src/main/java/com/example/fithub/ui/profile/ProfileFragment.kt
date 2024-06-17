package com.example.fithub.ui.profile

import SharedPreferencesManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fithub.LoginActivity
import com.example.fithub.MainActivity
import com.example.fithub.api.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.example.fithub.databinding.FragmentProfileBinding
import com.example.fithub.models.UserProfile
import com.example.fithub.ui.workout.WorkoutPlanFragmentDirections

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPreferencesManager = SharedPreferencesManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.logoutButton.setOnClickListener {
            logout(sharedPreferencesManager)
        }
        val navController = findNavController()
        binding.editProfileButton.setOnClickListener{
            val action = ProfileFragmentDirections.actionNavigationProfileToProfileEditUserInfoFragment()
            navController.navigate(action)
        }
        loadUserProfile()
        return view
    }

    private fun loadUserProfile() {
        val jwtToken = sharedPreferencesManager.getAuthToken()
        val service = ServiceGenerator.authService

        val headers = HashMap<String, String>()
        jwtToken?.let { token ->
            headers["Authorization"] = "Bearer $token"
        }

        service.getProfile(headers).enqueue(object : Callback<UserProfile> {
            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                if (response.isSuccessful) {
                    val userProfile = response.body()
                    userProfile?.let {
                        updateUI(it)
                    } ?: run {
                        Toast.makeText(context, "Ошибка: профиль пользователя не найден", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Ошибка при загрузке профиля", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                Toast.makeText(context, "Ошибка сети", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUI(userProfile: UserProfile) {
        binding.fullname.text = "${userProfile.firstname ?: ""} ${userProfile.lastname ?: ""}"
        binding.city.text = userProfile.location ?: "Не указан"
        binding.aboutMe.text = userProfile.bio ?: "Нет информации"
        binding.dateOfBirth.text = "Дата рождения: ${userProfile.birthdate ?: "Не указана"}"
        binding.dateOfRegistration.text = "Дата регистрации: ${userProfile.registrationdate ?: "Не указана"}"
    }

    fun logout(sharedPreferencesManager: SharedPreferencesManager) {
        sharedPreferencesManager.clearAuthToken()

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
