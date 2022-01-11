package com.example.newpakingapp.utlis

import com.example.newpakingapp.data.model.ApkVersion
import com.example.newpakingapp.data.model.LoginResponse

sealed class ApiState {
    object Loading : ApiState()
    class Failure(val msg:Throwable) : ApiState()
    class Success(val data:ApkVersion) : ApiState()
    class SuccessLogin (val loginResponse: LoginResponse) :ApiState()
    object Empty : ApiState()
}
