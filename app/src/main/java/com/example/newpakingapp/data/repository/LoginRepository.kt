package com.example.newpakingapp.data.repository

import com.example.newpakingapp.data.api.ApiServiceImpl
import com.example.newpakingapp.data.database.DatabaseDao
import com.example.newpakingapp.data.database.DatabaseHelper
import com.example.newpakingapp.data.model.ApkVersionResponse
import com.example.newpakingapp.data.model.LoginResponse
import com.example.newpakingapp.data.model.Module
import com.example.newpakingapp.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiServiceImpl: ApiServiceImpl,private val databaseHelper: DatabaseHelper){

     fun getVersion(): Flow<ApkVersionResponse> = flow {
         emit(apiServiceImpl.getAppVersion())
     }.flowOn(Dispatchers.IO)

    fun getLoginResponse(userLogin: HashMap<String, String>) :Flow<LoginResponse> = flow {
        emit(apiServiceImpl.userLogin(userLogin))
    }.flowOn(Dispatchers.IO)

    suspend fun insertUser(user: User) {
        databaseHelper.insertUser(user)
    }

    suspend fun insertModules(modules: List<Module>)
    {
        databaseHelper.insertModule(modules)
    }

    suspend fun deleteAllUsers(){
        databaseHelper.deleteAllUsers()
    }

    suspend fun deleteAllModules(){
        databaseHelper.deleteAllModules()
    }

}