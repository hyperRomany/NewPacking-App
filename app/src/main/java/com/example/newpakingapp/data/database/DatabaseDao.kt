package com.example.newpakingapp.data.database

import androidx.room.*
import com.example.newpakingapp.data.model.*
import dagger.Provides
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {

    //------------------------------ login Activity-----------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertModules(modules: List<Module>)

    @Query("Delete from module_table")
    suspend fun deleteAllModules()

    @Query("Delete from user_table")
    suspend fun deleteAllUsers()

    //------------------------------ home Activity-----------------------------

    @Query("Select * from module_table")
    suspend fun getAllModules() : List<Module>

    //------------------------------ start order Activity-----------------------------

    @Query("SELECT * FROM header_table where order_number =:orderNumber")
    suspend fun getHeaderToUpload(orderNumber: String?): List<OrderHeaderModule>

    @Query("SELECT * FROM orderDetailsItemsScanned_table where TrackingNumber is not null or TrackingNumber !=''")
    suspend fun getExistingOrders() : List<OrderDetailsItemsScanned>

    @Query("SELECT * FROM HEADER_TABLE")
    suspend fun getAllOrdersInDatabase() : List<OrderHeaderModule>

    @Query("DELETE FROM header_table where Order_number =:Order_number")
    suspend fun deleteAllHeader(Order_number: String?)

    @Query("DELETE FROM orderDetails_table where Order_number =:Order_number")
    suspend fun deleteAllOrderItems(Order_number: String?)

    @Query("DELETE FROM trackingNumber_table where Ordernumber =:Order_number")
    suspend fun deleteAllTrackingNumber(Order_number: String?)

    @Query("DELETE FROM orderDetailsItemsScanned_table where Order_number =:Order_number")
    suspend fun deleteOrderItemsScanned(Order_number: String?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderHeader(orderHeader: OrderHeaderModule?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderDetails(orderDetails: OrderItemsDetails?)

    @Query("SELECT * FROM orderDetails_table D, orderDetailsItemsScanned_table S \n" +
            "where (D.sku not in (select sku from orderDetailsItemsScanned_table) \n" +
            "or D.quantity != S.quantity) And D.Order_number = S.Order_number And D.Order_number =:orderNumber")
    suspend fun getAllItemsNotScannedOrNotEqualQuantity(orderNumber: String) : List<OrderItemsDetails>

    //------------------------------ Pack Package Activity-----------------------------

    @Query("SELECT * FROM orderDetails_table WHERE Order_number = :orderNumber")
    suspend fun getAllItemsInOrder(orderNumber: String) : List<OrderItemsDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScannedItem(orderDetailsItemsScanned: OrderDetailsItemsScanned)

}