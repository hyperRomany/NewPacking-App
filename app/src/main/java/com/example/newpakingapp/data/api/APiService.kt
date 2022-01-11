package com.example.newpakingapp.data.api

import com.example.newpakingapp.data.model.ApkVersion
import com.example.newpakingapp.data.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APiService {

    @POST("Version/GetVersion.php")
    suspend fun getVersions() : ApkVersion

    @POST("Login/Auth.php")
    suspend fun login(@Body loginUser: HashMap<String, String>) : LoginResponse


}