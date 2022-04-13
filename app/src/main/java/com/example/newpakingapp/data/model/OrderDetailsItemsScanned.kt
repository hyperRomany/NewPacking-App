package com.example.newpakingapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "orderDetailsItemsScanned_table")
data class OrderDetailsItemsScanned(

    @PrimaryKey(autoGenerate = true)
    var itemId: Int = 0,

    @ColumnInfo(name = "Order_number")
    @SerializedName("Order_number")
    var Order_number: String? = null,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String? = null,

    @ColumnInfo(name = "price")
    @SerializedName("price")
    var price: Float = 0f,

    @ColumnInfo(name = "quantity")
    @SerializedName("quantity")
    var quantity: Float = 0f,


    @ColumnInfo(name = "sku")
    @SerializedName("sku")
    var barcode: String? = null,


    @ColumnInfo(name = "unit_of_measurement")
    @SerializedName("unit_of_measurement")
    var unitOfMeasure: String? = null,

    @ColumnInfo(name = "TrackingNumber")
    @SerializedName("TrackingNumber")
    var trackingNumber: String? = null

)
