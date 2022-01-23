package com.example.newpakingapp.data.model

import com.google.gson.annotations.SerializedName

data class OrderDataResponse(
    @SerializedName("order_number")
    var order_number: String? = null,

    @SerializedName("customer")
    val customer: Customer? = null,

    @SerializedName("delivery")
    val delivery: Delivery? = null,

    @SerializedName("grand_total")
    val grand_total: String? = null,

    @SerializedName("shipping_fees")
    val shipping_fees: Float? = 0f,

    @SerializedName("picker_confirmation_time")
    val picker_confirmation_time: String? = null,

    @SerializedName("currency")
    val currency: String? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("outbound_delivery")
    var OutBound_delivery: String? = null,

    @SerializedName("out_from_site")
    val Out_From_Loc: String? = null,

    @SerializedName("items")
    val itemsOrderDataDBDetails: List<OrderItemsDetails>? = null,

    @SerializedName("reedemed_points_amount")
    val reedemed_points_amount: String? = null,

    @SerializedName("payment_method")
    val payment_method: String? = null

)
