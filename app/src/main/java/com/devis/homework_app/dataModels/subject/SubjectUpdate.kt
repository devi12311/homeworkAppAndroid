package com.devis.homework_app.dataModels.subject

import android.text.Editable
import com.devis.homework_app.dataModels.absence.AbsenceResponse
import com.devis.homework_app.dataModels.exam.ExamResponse
import com.devis.homework_app.dataModels.homework.HomeworkResponse
import com.google.gson.annotations.SerializedName

data class SubjectUpdate(

    @SerializedName("name")
    var courseName: String,

    @SerializedName("startDate")
    var startDate: String,

    @SerializedName("endDate")
    var endDate: String,

    )