package com.devis.homework_app.dataModels.exam

import com.google.gson.annotations.SerializedName

data class ExamResponse (
    @SerializedName("id")
    var id : String,

    @SerializedName("userId")
    var userId : String,

    @SerializedName("subjectId")
    var subjectId : String,

    @SerializedName("grade")
    var grade : String,

    @SerializedName("startDate")
    var startDate : String,

    @SerializedName("endDate")
    var endDate : String,

    @SerializedName("createdAt")
    var createdAt : String,

    @SerializedName("updatedAt")
    var updatedAt : String,
)