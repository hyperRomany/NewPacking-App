package com.example.newpakingapp.data.repository

import androidx.annotation.WorkerThread
import androidx.room.FtsOptions
import com.example.newpakingapp.data.api.ApiService
import com.example.newpakingapp.data.api.ApiServiceImpl
import com.example.newpakingapp.data.database.DatabaseDao
import com.example.newpakingapp.data.database.DatabaseHelper
import com.example.newpakingapp.data.model.OrderDataResponse
import com.example.newpakingapp.data.model.OrderHeaderModule
import com.example.newpakingapp.data.model.OrderItemsDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StartOrderRepository @Inject constructor(private val databaseHelper: DatabaseHelper, private val apiServiceImpl: ApiServiceImpl) {


    fun getOrderHeader(orderNumber:String): Flow<List<OrderHeaderModule>> = flow {
        emit(databaseHelper.getAllHeader(orderNumber))
    }.flowOn(Dispatchers.IO)


    suspend fun deleteAllHeader(orderNumber: String)
    {
        databaseHelper.deleteAllHeader(orderNumber)
    }


    suspend fun deleteAllOrderItems(orderNumber: String)
    {
        databaseHelper.deleteAllOrderItems(orderNumber)
    }

    @WorkerThread
    suspend fun deleteAllTrackingNumber(orderNumber: String)
    {
        databaseHelper.deleteAllTrackingNumber(orderNumber)
    }

    @WorkerThread
    suspend fun deleteOrderItemsScanned(orderNumber: String)
    {
        databaseHelper.deleteOrderItemsScanned(orderNumber)
    }

    @WorkerThread
    suspend fun insertOrderHeader(orderHeaderModule: OrderHeaderModule)
    {
        databaseHelper.insertOrderHeader(orderHeaderModule)
    }

    @WorkerThread
    suspend fun insertOrderDetails(orderDetails: OrderItemsDetails)
    {
        databaseHelper.insertOrderDetail(orderDetails)
    }


    fun getOrderDataResponse(orderNumber: String) : Flow<OrderDataResponse> = flow {
        emit(apiServiceImpl.getOrderAllData(orderNumber))
    }.flowOn(Dispatchers.IO)

}