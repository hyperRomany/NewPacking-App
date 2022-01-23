package com.example.newpakingapp.data.repository

import com.example.newpakingapp.data.database.DatabaseHelper
import com.example.newpakingapp.data.model.Module
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepository @Inject constructor(private val databaseHelper: DatabaseHelper){

    fun allModules(): Flow<List<Module>> = flow {
        emit(databaseHelper.getAllModules())
    }.flowOn(Dispatchers.IO)
}