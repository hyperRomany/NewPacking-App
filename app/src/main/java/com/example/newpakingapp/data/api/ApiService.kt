package com.example.newpakingapp.data.api

import com.example.newpakingapp.data.model.ApkVersionResponse
import com.example.newpakingapp.data.model.LoginResponse
import com.example.newpakingapp.data.model.OrderDataResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST("Version/GetVersion.php")
    suspend fun getVersions() : ApkVersionResponse

    @POST("Login/Auth.php")
    suspend fun login(@Body loginUser: HashMap<String, String>) : LoginResponse

    @FormUrlEncoded
    @POST("GetMagentoOrderDetails.php")
    suspend fun getOrderData(@Field("number") orderNumber: String?, @Field("token") token: String?): OrderDataResponse

}