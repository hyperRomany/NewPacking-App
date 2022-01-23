package com.example.newpakingapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "header_table")
data class OrderHeaderModule (
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,

    @ColumnInfo(name = "order_number")
    @SerializedName("Order_number")
    var orderNumber: String? = null,

    @ColumnInfo(name = "Customer_name")
    @SerializedName("Customer_name")
    var Customer_name: String? = null,

    @ColumnInfo(name = "Customer_phone")
    @SerializedName("Customer_phone")
    var Customer_phone: String? = null,

    @ColumnInfo(name = "Customer_code")
    @SerializedName("Customer_code")
    var Customer_code: String? = null,


    @ColumnInfo(name = "OutBound_delivery")
    @SerializedName("OutBound_delivery")
    var OutBound_delivery: String? = null,

    @ColumnInfo(name = "Customer_address_govern")
    @SerializedName("Customer_address_govern")
    var Customer_address_govern: String? = null,


    @ColumnInfo(name = "Customer_address_city")
    @SerializedName("Customer_address_city")
    var Customer_address_city: String? = null,


    @ColumnInfo(name = "Customer_address_district")
    @SerializedName("Customer_address_district")
    var Customer_address_district: String? = null,

    @ColumnInfo(name = "Customer_address_detail")
    @SerializedName("Customer_address_detail")
    var Customer_address_detail: String? = null,


    @ColumnInfo(name = "delivery_date")
    @SerializedName("delivery_date")
    var delivery_date: String? = null,

    @ColumnInfo(name = "delivery_time")
    @SerializedName("delivery_time")
    var delivery_time: String? = null,

    @ColumnInfo(name = "grand_total")
    @SerializedName("grand_total")
    var grand_total: String? = null,

    @ColumnInfo(name = "shipping_fees")
    @SerializedName("shipping_fees")
    var shipping_fees: Float? = 0f,

    @ColumnInfo(name = "picker_confirmation_time")
    @SerializedName("picker_confirmation_time")
    var picker_confirmation_time: String? = null,

    @ColumnInfo(name = "currency")
    @SerializedName("currency")
    var currency: String? = null,

    @ColumnInfo(name = "Out_From_Loc")
    @SerializedName("Out_From_Loc")
    var Out_From_Loc: String? = null,

    @ColumnInfo(name = "reedemed_points_amount")
    @SerializedName("reedemed_points_amount")
    var reedemed_points_amount: String? = null,

    @ColumnInfo(name = "payment_method")
    @SerializedName("payment_method")
    var payment_method: String? = null,

    @ColumnInfo(name = "delivery_method")
    @SerializedName("delivery_method")
    var delivery_method: String? = null,

    )