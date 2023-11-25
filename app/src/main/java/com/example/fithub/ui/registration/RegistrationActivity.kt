package com.example.fithub.ui.registration
import SharedPreferencesManager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fithub.MainActivity
import com.example.fithub.R
import com.example.fithub.api.ServiceGenerator.authService
import com.example.fithub.models.AuthResponse
import com.example.fithub.models.RegistrationModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val confirmPasswordEditText = findViewById<EditText>(R.id.editTextConfirmPassword)
        val firstnameEditText = findViewById<EditText>(R.id.editTextFirstname)
        val lastnameEditText = findViewById<EditText>(R.id.editTextLastname)
        val registerButton = findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            val firstname = firstnameEditText.text.toString()
            val lastname = lastnameEditText.text.toString()

            if (password != confirmPassword) {
                showToast("Пароли не совпадают")
                return@setOnClickListener
            }

            val registrationModel = RegistrationModel(
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                firstname = firstname,
                lastname = lastname
            )

            registerUser(registrationModel)
        }
    }

    private fun registerUser(registrationModel: RegistrationModel) {
        val call = authService.registerUser(registrationModel)
        val sharedPreferencesManager = SharedPreferencesManager(this)

        call.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val authResponse = response.body()
                    if (authResponse != null) {
                        sharedPreferencesManager.saveAuthToken(authResponse.token)

                        val intent = Intent(this@RegistrationActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        showToast("Наизвестный ответ от сервера")
                    }
                } else {
                    showToast("Ошибка регистрации")
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                showToast("Ошибка сети: ${t.message}")
            }
        })
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this@RegistrationActivity, message, Toast.LENGTH_LONG).show()
        }
    }
}
