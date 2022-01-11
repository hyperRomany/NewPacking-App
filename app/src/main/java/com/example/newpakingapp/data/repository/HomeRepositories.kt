package com.example.newpakingapp.data.repository

import com.example.newpakingapp.data.api.APiService
import com.example.newpakingapp.data.database.DatabaseDao
import com.example.newpakingapp.data.model.Module
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositories @Inject constructor(private val databaseDao: DatabaseDao) {

    val allModules: Flow<List<Module>> = databaseDao.getAllModules()
}