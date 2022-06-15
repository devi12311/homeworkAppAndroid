package com.devis.homework_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.devis.homework_app.dataModels.auth.LoginRequest
import com.devis.homework_app.dataModels.auth.LoginResponse
import com.devis.homework_app.dataModels.auth.RegisterRequest
import com.devis.homework_app.dataModels.auth.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_register)

        apiClient = ApiClient()

        val username = findViewById<EditText>(R.id.etRegisterUsername)
        val password = findViewById<EditText>(R.id.etRegisterPassword)
        val email = findViewById<EditText>(R.id.etRegisterEmail)

        val registerButton = findViewById<Button>(R.id.btRegisterSubmit)

        registerButton.setOnClickListener {
            apiClient.getApiService().register(RegisterRequest(username = username.text.toString(), password = password.text.toString(), email = email.text.toString()))
                .enqueue(object : Callback<RegisterResponse> {
                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        println("failed")
                    }

                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        val registerResponse = response.body();
                        Toast.makeText(applicationContext, registerResponse!!.message, Toast.LENGTH_LONG).show()
                        finish()
                    }
                })
        }
    }
}