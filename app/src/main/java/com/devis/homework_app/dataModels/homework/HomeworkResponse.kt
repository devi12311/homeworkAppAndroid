package com.devis.homework_app.dataModels.homework

import com.google.gson.annotations.SerializedName

data class HomeworkResponse (
    @SerializedName("id")
    var id : String,

    @SerializedName("title")
    var title : String,

    @SerializedName("subjectId")
    var subjectId : String,

    @SerializedName("userId")
    var userId : String,

    @SerializedName("description")
    var description : String,

    @SerializedName("document")
    var document : String,

    @SerializedName("status")
    var status : Boolean,

    @SerializedName("startDate")
    var startDate : String,

    @SerializedName("endDate")
    var endDate : String,

    @SerializedName("createdAt")
    var createdAt : String,

    @SerializedName("updatedAt")
    var updatedAt : String,
)