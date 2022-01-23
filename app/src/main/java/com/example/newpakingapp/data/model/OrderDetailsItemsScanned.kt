package com.example.newpakingapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "orderDetailsItemsScanned_table")
data class OrderDetailsItemsScanned(
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,

    @ColumnInfo(name = "Order_number")
    @SerializedName("Order_number")
    val Order_number: String? = null,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String? = null,

    @ColumnInfo(name = "price")
    @SerializedName("price")
    val price: Float = 0f,

    @ColumnInfo(name = "quantity")
    @SerializedName("quantity")
    val quantity: Float = 0f,


    @ColumnInfo(name = "sku")
    @SerializedName("sku")
    val barcode: String? = null,


    @ColumnInfo(name = "unit_of_measurement")
    @SerializedName("unit_of_measurement")
    val unite: String? = null,

    @ColumnInfo(name = "TrackingNumber")
    @SerializedName("TrackingNumber")
    val TrackingNumber: String? = null

)
