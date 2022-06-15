package com.devis.homework_app.dataModels.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse (

    @SerializedName("accessToken")
    var authToken: String,

    @SerializedName("message")
    var message: String,
)