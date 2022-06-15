package com.devis.homework_app

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.devis.homework_app.dataModels.subject.SubjectResponse
import com.devis.homework_app.dataModels.subject.SubjectUpdate
import kotlinx.android.synthetic.main.edit_subject_activity.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class EditSubjectActivity  : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.edit_subject_activity)

        apiClient = ApiClient()
        sessionManager = SessionManager(this)

        val pickStartDate = findViewById<Button>(R.id.startDate)
        val pickEndDate = findViewById<Button>(R.id.endDate)
        val subjectName = findViewById<EditText>(R.id.homeworkTitle);

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dashboardIntent = Intent(this, Dashboard::class.java)
        val createSubjectIntent = Intent(this, CreateSubjectActivity::class.java)

        pickStartDate.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, _, dayOfMonth ->
                pickStartDate.text = "$year-$month-$dayOfMonth"
            }, year, month, day)
            dpd.show()
        }

        pickEndDate.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, _, dayOfMonth ->
                pickEndDate.text = "$year-$month-$dayOfMonth"
            }, year, month, day)
            dpd.show()
        }

        createButton.setOnClickListener {
            startActivity(createSubjectIntent)
        }

        updateButton.setOnClickListener {

            val id = intent.getStringExtra("id");
            val token = "Bearer " + sessionManager.fetchAuthToken()

            val subjectPayload = SubjectUpdate (
                        courseName = subjectName.text.toString(),
                        startDate = pickStartDate.text as String,
                        endDate = pickEndDate.text as String
                    )

            apiClient.getApiService().updateSubject(
                token = token,
                id = id?.toInt(),
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

                        if (statusCode == 200) {
                            Toast.makeText(this@EditSubjectActivity, "Subject updated successfully!", Toast.LENGTH_LONG).show()
                            startActivity(dashboardIntent)
                        } else {
                            val errorObject = JSONObject(response.errorBody()!!.charStream().readText())
                            Toast.makeText(applicationContext, errorObject.getString("message"), Toast.LENGTH_LONG).show()
                        }
                    }

                })
        }

        getSubject()
    }

    private fun getSubject() {

        val id = intent.getStringExtra("id");
        val token = "Bearer " + sessionManager.fetchAuthToken()

        apiClient.getApiService().getSubject(token = token, id = id?.toInt())
            ?.enqueue(object : Callback<SubjectResponse> {
                override fun onFailure(call: Call<SubjectResponse>, t: Throwable) {
                    println(t)
                }

                override fun onResponse(
                    call: Call<SubjectResponse>,
                    response: Response<SubjectResponse>
                ) {
                    val subjectResponse : SubjectResponse? = response.body()
                    val statusCode = response.code()

                    if (statusCode == 200) {

                        homeworkTitle.setText(subjectResponse?.courseName)
                        startDate.text = subjectResponse?.startDate
                        endDate.text = subjectResponse?.endDate

                    } else {
                        val errorObject = JSONObject(response.errorBody()!!.charStream().readText())
                        Toast.makeText(applicationContext, errorObject.getString("message"), Toast.LENGTH_LONG).show()
                    }
                }

            })
    }

}