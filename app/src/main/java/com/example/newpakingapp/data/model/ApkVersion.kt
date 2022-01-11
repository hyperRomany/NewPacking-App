package com.example.newpakingapp.data.model

import com.google.gson.annotations.SerializedName




data class ApkVersion(

    @SerializedName("ID")
    val ID: String? = null,

    @SerializedName("Version_Code")
    var Version_Code: String? = null,

    @SerializedName("Version_Name")
    var Version_Name: String? = null

)
