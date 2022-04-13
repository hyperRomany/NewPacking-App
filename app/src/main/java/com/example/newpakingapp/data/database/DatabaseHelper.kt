package com.example.newpakingapp.data.database

import com.example.newpakingapp.data.model.*
import javax.inject.Inject

class DatabaseHelper @Inject constructor(private val databaseDao: DatabaseDao) {

    suspend fun insertUser(user: User) = databaseDao.insertUser(user)

    suspend fun insertModule(modules: List<Module>) = databaseDao.insertModules(modules)

    suspend fun deleteAllUsers() = databaseDao.deleteAllUsers()

    suspend fun deleteAllModules() = databaseDao.deleteAllModules()

    suspend fun getAllModules() : List<Module> = databaseDao.getAllModules()

    suspend fun getOrderByNumberToUpload(orderNumber: String): List<OrderHeaderModule> = databaseDao.getHeaderToUpload(orderNumber)

    suspend fun getAllOrderHeaders() : List<OrderHeaderModule> = databaseDao.getAllOrdersInDatabase()

    suspend fun deleteAllHeader(orderNumber: String) = databaseDao.deleteAllHeader(orderNumber)

    suspend fun deleteAllOrderItems(orderNumber: String) = databaseDao.deleteAllOrderItems(orderNumber)

    suspend fun deleteAllTrackingNumber(orderNumber: String) = databaseDao.deleteAllTrackingNumber(orderNumber)

    suspend fun deleteOrderItemsScanned(orderNumber: String) = databaseDao.deleteOrderItemsScanned(orderNumber)

    suspend fun getExistingOrders() = databaseDao.getExistingOrders()

    suspend fun insertOrderHeader(orderHeader: OrderHeaderModule) = databaseDao.insertOrderHeader(orderHeader)

    suspend fun insertOrderDetail(orderItemsDetails: OrderItemsDetails) = databaseDao.insertOrderDetails(orderItemsDetails)

    suspend fun getAllItemsInOrder(orderNumber: String) = databaseDao.getAllItemsInOrder(orderNumber)

    suspend fun insertScannedItem(orderDetailsItemsScanned: OrderDetailsItemsScanned) = databaseDao.insertScannedItem(orderDetailsItemsScanned)

}