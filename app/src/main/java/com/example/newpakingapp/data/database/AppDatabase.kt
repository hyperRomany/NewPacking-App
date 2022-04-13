package com.example.newpakingapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newpakingapp.data.model.*
import com.example.newpakingapp.utlis.DATABASE_NAME


@Database(entities = [User::class, Module::class, OrderHeaderModule::class,
    OrderItemsDetails::class, OrderDetailsItemsScanned::class, TrackingNumber::class], version = 6, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun getDao() : DatabaseDao


    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(
                    context
                    //mDataBase
                ).also { instance = it }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}

