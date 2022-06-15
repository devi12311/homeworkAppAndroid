package com.devis.homework_app

import com.google.gson.annotations.SerializedName

data class User (

    @SerializedName("id")
    var id: String,

    @SerializedName("username")
    var firstName: String,

    @SerializedName("email")
    var email: String

)