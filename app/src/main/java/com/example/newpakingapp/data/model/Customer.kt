package com.example.newpakingapp.data.model

import com.google.gson.annotations.SerializedName

data class Customer(
    @SerializedName("name")
    var name: String? = null,

    @SerializedName("phone_number")
    val phone_number: String? = null,


    @SerializedName("address")
    val address: Address? = null,


    @SerializedName("customer_code")
    val customer_code: String? = null
)
