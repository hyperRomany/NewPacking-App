package com.example.newpakingapp.data.model

import com.google.gson.annotations.SerializedName

data class Delivery(
    @SerializedName("date")
    var date: String? = null,

    @SerializedName("time")
    val time: String? = null,

    @SerializedName("method")
    val method: String? = null
)
