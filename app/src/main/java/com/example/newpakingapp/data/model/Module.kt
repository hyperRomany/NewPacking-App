package com.example.newpakingapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "module_table")
data class Module(
    @PrimaryKey(autoGenerate = true)
    val modId : Int,

    @ColumnInfo(name = "Modules_ID")
    @SerializedName("Modules_ID")
    var Modules_ID: String? = null
)
