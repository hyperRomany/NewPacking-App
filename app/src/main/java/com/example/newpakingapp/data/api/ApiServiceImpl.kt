package com.example.newpakingapp.data.api

import com.example.newpakingapp.data.model.ApkVersionResponse
import com.example.newpakingapp.data.model.LoginResponse
import com.example.newpakingapp.data.model.OrderDataResponse
import com.example.newpakingapp.utlis.MAGENTO_TOKEN
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) {

    suspend fun getAppVersion(): ApkVersionResponse = apiService.getVersions()

    suspend fun userLogin(loginUser: HashMap<String, String>) : LoginResponse = apiService.login(loginUser)

    suspend fun getOrderAllData(orderNumber:String):OrderDataResponse = apiService.getOrderData(orderNumber, MAGENTO_TOKEN)
}