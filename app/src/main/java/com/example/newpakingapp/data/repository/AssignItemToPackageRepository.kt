package com.example.newpakingapp.data.repository

import com.example.newpakingapp.data.database.DatabaseHelper
import com.example.newpakingapp.data.model.OrderDetailsItemsScanned
import com.example.newpakingapp.data.model.OrderItemsDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AssignItemToPackageRepository @Inject constructor(private val databaseHelper: DatabaseHelper) {
    fun getAllItemsIOrder(orderNumber : String) :Flow<List<OrderItemsDetails>> = flow {
        emit(databaseHelper.getAllItemsInOrder(orderNumber))
    }.flowOn(Dispatchers.IO)

    fun insertScannedItem(orderDetailsItemsScanned: OrderDetailsItemsScanned) = flow{
        emit(databaseHelper.insertScannedItem(orderDetailsItemsScanned))
    }.flowOn(Dispatchers.IO)
}