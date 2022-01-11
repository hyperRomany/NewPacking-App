package com.example.newpakingapp.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("records")
    val userInfo:List<User> ? = null,

    @SerializedName("Modules")
    val modules:List<Module> ?  = null
)
