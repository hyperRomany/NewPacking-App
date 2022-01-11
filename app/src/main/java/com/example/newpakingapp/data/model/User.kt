package com.example.newpakingapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(

    @PrimaryKey (autoGenerate = true)
    val uid:Int,

    @ColumnInfo(name = "User_Department")
    @SerializedName("User_Department")
    var userDepartment: String? = null,

    @ColumnInfo(name = "User_status")
    @SerializedName("User_status")
    val userStatus: String? = null,

    @ColumnInfo(name = "ComplexID")
    @SerializedName("ComplexID")
    val complexID: String? = null,

    @ColumnInfo(name = "Group_Name")
    @SerializedName("Group_Name")
    val groupName: String? = null,

    @ColumnInfo(name = "company")
    @SerializedName("company")
    val company: String? = null,

    @ColumnInfo(name = "id")
    @SerializedName("id")
    val user_id: String? = null,

    @ColumnInfo(name = "User_Description")
    @SerializedName("User_Description")
    val userDescription: String? = null,

    @ColumnInfo(name = "GroupID")
    @SerializedName("GroupID")
    val groupID: String? = null
)
