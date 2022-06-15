package com.devis.homework_app.dataModels.absence

import com.google.gson.annotations.SerializedName

data class AbsenceResponse (
    @SerializedName("id")
    var id : String,

    @SerializedName("userId")
    var userId : String,

    @SerializedName("subjectId")
    var subjectId : String,

    @SerializedName("data")
    var date : String,

    @SerializedName("createdAt")
    var createdAt : String,

    @SerializedName("updatedAt")
    var updatedAt : String,
)