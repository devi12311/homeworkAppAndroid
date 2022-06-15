package com.devis.homework_app.dataModels.subject

import com.google.gson.annotations.SerializedName

data class SubjectArrayResponse (

    @SerializedName("data")
    val data: List<SubjectResponse>,

)