package com.devis.homework_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.devis.homework_app.dataModels.auth.LoginRequest
import com.devis.homework_app.dataModels.auth.LoginResponse
import org.json.JSONObject
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        apiClient = ApiClient()
        sessionManager = SessionManager(this)

        val username = findViewById<EditText>(R.id.etUsername)
        val password = findViewById<EditText>(R.id.etPassword)

        val loginButton = findViewById<Button>(R.id.btLogin)
        val registerButton = findViewById<Button>(R.id.btRegister)
        val intent = Intent(this, RegisterActivity::class.java);
        val dashboardIntent = Intent(this, Dashboard::class.java);

        loginButton.setOnClickListener {
            apiClient.getApiService().login(LoginRequest(username = username.text.toString(), password = password.text.toString()))
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        println("failed")
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        val loginResponse = response.body()
                        val statusCode = response.code()

                            if (statusCode == 200) {
                                println(loginResponse!!.authToken)
                                sessionManager.saveAuthToken(loginResponse.authToken)
                                startActivity(dashboardIntent)
                            }
                            else {
                                val errorObject = JSONObject(response.errorBody()!!.charStream().readText())
                                Toast.makeText(applicationContext, errorObject.getString("message"), Toast.LENGTH_LONG).show()
                            }
                        }

                })
        }

        registerButton.setOnClickListener {
            startActivity(intent);
        }
    }
}