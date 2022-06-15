package com.devis.homework_app

import com.devis.homework_app.dataModels.auth.LoginRequest
import com.devis.homework_app.dataModels.auth.LoginResponse
import com.devis.homework_app.dataModels.auth.RegisterRequest
import com.devis.homework_app.dataModels.auth.RegisterResponse
import com.devis.homework_app.dataModels.homework.HomeworkResponse
import com.devis.homework_app.dataModels.subject.SubjectArrayResponse
import com.devis.homework_app.dataModels.subject.SubjectResponse
import com.devis.homework_app.dataModels.subject.SubjectUpdate
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST(Constants.LOGIN_URL)
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST(Constants.SIGNUP_URL)
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @GET(Constants.GET_SUBJECTS_URL)
    fun getSubjects(
        @Header("Authorization") token: String?
    ): Call<List<SubjectResponse>>?

    @GET(Constants.GET_SUBJECT_URL)
    fun getSubject(
        @Header("Authorization") token: String?,
        @Path("id") id: Int?
    ): Call<SubjectResponse>?

    @GET(Constants.GET_HOMEWORKS_URL)
    fun getHomeworks(
        @Header("Authorization") token: String?
    ): Call<List<HomeworkResponse>>?

    @Headers("Content-Type: application/json")
    @PUT(Constants.GET_SUBJECT_URL)
    fun updateSubject(
        @Body request: SubjectUpdate,
        @Header("Authorization") token: String?,
        @Path("id") id: Int?
    ): Call<SubjectUpdate>

    @Headers("Content-Type: application/json")
    @POST(Constants.CREATE_SUBJECT_URL)
    fun createSubject(
        @Body request: SubjectUpdate,
        @Header("Authorization") token: String?,
    ): Call<SubjectUpdate>
}