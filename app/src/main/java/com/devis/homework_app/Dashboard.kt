package com.devis.homework_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devis.homework_app.adapters.HomeworkAdapter
import com.devis.homework_app.adapters.SubjectAdapter
import com.devis.homework_app.dataModels.homework.HomeworkResponse
import com.devis.homework_app.dataModels.subject.SubjectResponse
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Dashboard : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_dashboard)

        apiClient = ApiClient()
        sessionManager = SessionManager(this)


        getSubjects()
        getHomeworks()
    }

    private fun getSubjects(): List<SubjectResponse> {

        val modeList = mutableListOf<SubjectResponse>()
        val token = "Bearer " + sessionManager.fetchAuthToken()

        apiClient.getApiService().getSubjects(token = token)
            ?.enqueue(object : Callback<List<SubjectResponse>> {
                override fun onFailure(call: Call<List<SubjectResponse>>, t: Throwable) {
                    println(t)
                }

                override fun onResponse(
                    call: Call<List<SubjectResponse>>,
                    response: Response<List<SubjectResponse>>
                ) {
                    val subjectResponse = response.body()
                    val statusCode = response.code()

                    if (statusCode == 200) {
                        val subjects : List<SubjectResponse>? = subjectResponse;
                        val adapter = subjects?.let { SubjectAdapter(it, this@Dashboard) }

                        subjectRcv.layoutManager = LinearLayoutManager(this@Dashboard, RecyclerView.HORIZONTAL, false)
                        subjectRcv.adapter = adapter;

                        adapter?.setOnClickListener(object : SubjectAdapter.ClickListener{
                            override fun onClick(pos: Int, aView: View) {
                                Toast.makeText(this@Dashboard, subjects[pos].courseName, Toast.LENGTH_LONG).show()
                                val editSubjectIntent = Intent(this@Dashboard, EditSubjectActivity::class.java);
                                editSubjectIntent.putExtra("id",subjects[pos].id)
                                startActivity(editSubjectIntent)
                            }
                        })

                    } else {
                        val errorObject = JSONObject(response.errorBody()!!.charStream().readText())
                        Toast.makeText(applicationContext, errorObject.getString("message"), Toast.LENGTH_LONG).show()
                    }
                }

            })
        return modeList
    }

    private fun getHomeworks(): List<HomeworkResponse> {

        val modeList = mutableListOf<HomeworkResponse>()
        val token = "Bearer " + sessionManager.fetchAuthToken()

        apiClient.getApiService().getHomeworks(token = token)
            ?.enqueue(object : Callback<List<HomeworkResponse>> {
                override fun onFailure(call: Call<List<HomeworkResponse>>, t: Throwable) {
                    println(t)
                }

                override fun onResponse(
                    call: Call<List<HomeworkResponse>>,
                    response: Response<List<HomeworkResponse>>
                ) {
                    val homeworkResponse = response.body()
                    val statusCode = response.code()

                    if (statusCode == 200) {
                        val subjects : List<HomeworkResponse>? = homeworkResponse;
                        val adapter = subjects?.let { HomeworkAdapter(it, this@Dashboard) }

                        homeworkRcv.layoutManager = LinearLayoutManager(this@Dashboard, RecyclerView.VERTICAL, false)
                        homeworkRcv.adapter = adapter;

                        adapter?.setOnClickListener(object : HomeworkAdapter.ClickListener{
                            override fun onClick(pos: Int, aView: View) {
                            }
                        })

                    } else {
                        val errorObject = JSONObject(response.errorBody()!!.charStream().readText())
                        Toast.makeText(applicationContext, errorObject.getString("message"), Toast.LENGTH_LONG).show()
                    }
                }

            })
        return modeList
    }
}
