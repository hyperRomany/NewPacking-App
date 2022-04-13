package com.example.newpakingapp.utlis

import com.example.newpakingapp.data.model.*

sealed class DataStateFlow {
    object Loading : DataStateFlow()

    class Failure(val msg:Throwable) : DataStateFlow()

    object Empty : DataStateFlow()

    object Success: DataStateFlow()

    // for Version Calling
    class VersionResponseSuccess(val data:ApkVersionResponse) : DataStateFlow()

    // for login
    class LoginSuccessResponse (val loginResponse: LoginResponse) : DataStateFlow()

    //for get order Data
    class GetOrderDataSuccess(val orderDataResponse: OrderDataResponse) : DataStateFlow()

    //for select headers from database
    class GetHeaderModuleSuccess(val orderHeaderModules: List<OrderHeaderModule>) : DataStateFlow()

    class GetAllModules(val allModules : List<Module>) : DataStateFlow()

    class GetAllExistingOrders(val existingOrders : List<OrderDetailsItemsScanned>) : DataStateFlow()

    class GetAllItemsInDetails(val  allItems: List<OrderItemsDetails>) : DataStateFlow()

}
