package com.example.newpakingapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "trackingNumber_table")
data class TrackingNumber(

    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,

    @ColumnInfo(name = "OrderNumber")
    @SerializedName("OrderNumber")
    val OrderNumber: String? = null,

    @ColumnInfo(name = "TrackingNumber")
    @SerializedName("TrackingNumber")
    val TrackingNumber: String? = null
)
