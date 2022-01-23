package com.example.newpakingapp.data.model

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("govern")
    var govern: String? = null,

    @SerializedName("city")
    var city: String? = null,

    @SerializedName("district")
    var district: String? = null,

    @SerializedName("shipping_address")
    var customer_address_detail: String? = null
)
