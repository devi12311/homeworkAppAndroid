package com.devis.homework_app.dataModels.auth

import android.text.Editable
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    var username: String,

    @SerializedName("password")
    var password: String
)