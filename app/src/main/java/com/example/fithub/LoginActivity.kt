package com.example.fithub

import SharedPreferencesManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.fithub.api.ServiceGenerator.authService
import com.example.fithub.models.AuthResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import android.widget.Toast
import com.example.fithub.models.LoginRequest
import com.example.fithub.ui.registration.RegistrationActivity


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val usernameEditText = findViewById<EditText>(R.id.editTextUsername)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerButton = findViewById<Button>(R.id.registerButton)

        val sharedPreferencesManager = SharedPreferencesManager(this)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            loginUser(username, password, sharedPreferencesManager)
        }
        registerButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String, sharedPreferencesManager: SharedPreferencesManager) {

        val loginRequest = LoginRequest(email = email, password = password)

        val call = authService.loginUser(loginRequest)
        call.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val authResponse = response.body()
                    if (authResponse != null) {
                        sharedPreferencesManager.saveAuthToken(authResponse.token)

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        handleLoginError("Неизвестный ответ от сервера")
                    }
                } else {
                    handleLoginError("Неверные учетные данные")
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                handleLoginError("Ошибка сети: ${t.message}")
            }
            private fun handleLoginError(errorMessage: String) {
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}
