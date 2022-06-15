package com.devis.homework_app.dataModels.subject

import com.devis.homework_app.dataModels.absence.AbsenceResponse
import com.devis.homework_app.dataModels.exam.ExamResponse
import com.devis.homework_app.dataModels.homework.HomeworkResponse
import com.google.gson.annotations.SerializedName

data class SubjectResponse (

    @SerializedName("id")
    var id : String,

    @SerializedName("userId")
    var userId : String,

    @SerializedName("name")
    var courseName : String,

    @SerializedName("startDate")
    var startDate : String,

    @SerializedName("endDate")
    var endDate : String,

    @SerializedName("createdAt")
    var createdAt : String,

    @SerializedName("updatedAt")
    var updatedAt : String,

    @SerializedName("homeworks")
    var homeworks : List<HomeworkResponse>,

    @SerializedName("absences")
    var absences : List<AbsenceResponse>,

    @SerializedName("exams")
    var exams : List<ExamResponse>,
)