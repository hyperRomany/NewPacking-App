package com.example.newpakingapp.data.database

import androidx.room.*
import com.example.newpakingapp.data.model.Module
import com.example.newpakingapp.data.model.User
import dagger.Provides
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertModules(modules: List<Module>)

    @Query("Delete from module")
    suspend fun deleteAllModules()

    @Query("Delete from user")
    suspend fun deleteAllUsers()

    @Query("Select * from module")
    fun getAllModules() : Flow<List<Module>>

}