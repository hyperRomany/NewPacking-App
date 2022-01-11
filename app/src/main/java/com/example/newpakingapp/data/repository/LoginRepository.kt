package com.example.newpakingapp.data.repository

import com.example.newpakingapp.data.api.APiService
import com.example.newpakingapp.data.database.DatabaseDao
import com.example.newpakingapp.data.model.ApkVersion
import com.example.newpakingapp.data.model.LoginResponse
import com.example.newpakingapp.data.model.Module
import com.example.newpakingapp.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepository @Inject constructor(private val aPiService: APiService,private val databaseDao: DatabaseDao){

     fun getVersion(): Flow<ApkVersion> = flow {
         emit(aPiService.getVersions())
     }.flowOn(Dispatchers.IO)

    fun getLoginResponse(userLogin: HashMap<String, String>) :Flow<LoginResponse> = flow {
        emit(aPiService.login(userLogin))
    }.flowOn(Dispatchers.IO)

    suspend fun insertUser(user: User) {
        databaseDao.insertUser(user)
    }

    suspend fun insertModules(modules: List<Module>)
    {
        databaseDao.insertModules(modules)
    }

    suspend fun deleteAllUsers(){
        databaseDao.deleteAllUsers()
    }

    suspend fun deleteAllModules(){
        databaseDao.deleteAllModules()
    }

}