package com.devis.homework_app

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.devis.homework_app.dataModels.subject.SubjectUpdate
import kotlinx.android.synthetic.main.edit_subject_activity.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CreateSubjectActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_create_subject)

        apiClient = ApiClient()
        sessionManager = SessionManager(this)

        val pickStartDate = findViewById<Button>(R.id.startDate)
        val pickEndDate = findViewById<Button>(R.id.endDate)
        val subjectName = findViewById<EditText>(R.id.homeworkTitle);

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dashboardIntent = Intent(this, Dashboard::class.java);


        pickStartDate.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, year, _, dayOfMonth ->
                    pickStartDate.text = "$year-$month-$dayOfMonth"
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        pickEndDate.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, year, _, dayOfMonth ->
                    pickEndDate.text = "$year-$month-$dayOfMonth"
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        updateButton.setOnClickListener {

            val token = "Bearer " + sessionManager.fetchAuthToken()

            val subjectPayload = SubjectUpdate(
                courseName = subjectName.text.toString(),
                startDate = pickStartDate.text as String,
                endDate = pickEndDate.text as String
            )

            apiClient.getApiService().createSubject(
                token = token,
                request = subjectPayload
            )
                .enqueue(object : Callback<SubjectUpdate> {
                    override fun onFailure(call: Call<SubjectUpdate>, t: Throwable) {
                        println(t)
                    }

                    override fun onResponse(
                        call: Call<SubjectUpdate>,
                        response: Response<SubjectUpdate>
                    ) {
                        val statusCode = response.code()

                        if (statusCode == 200 || statusCode == 201) {
                            Toast.makeText(
                                this@CreateSubjectActivity,
                                "Subject created successfully!",
                                Toast.LENGTH_LONG
                            ).show()
                            startActivity(dashboardIntent)
                        } else {
                            val errorObject =
                                JSONObject(response.errorBody()!!.charStream().readText())
                            Toast.makeText(
                                applicationContext,
                                errorObject.getString("message"),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                })
        }
    }
}