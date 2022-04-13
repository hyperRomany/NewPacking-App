package com.example.newpakingapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newpakingapp.data.model.OrderHeaderModule
import com.example.newpakingapp.data.model.OrderItemsDetails
import com.example.newpakingapp.data.repository.StartOrderRepository
import com.example.newpakingapp.utlis.DataStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartOrderViewModel @Inject constructor(private val startOrderRepository: StartOrderRepository) : ViewModel() {

    private val responseStateFlowFlow: MutableStateFlow<DataStateFlow> =
        MutableStateFlow(DataStateFlow.Empty)
    val stateFlowFlowResponse: StateFlow<DataStateFlow> = responseStateFlowFlow

    // delete order header by orer number
    fun deleteAllHeader(orderNumber: String) = viewModelScope.launch {
        startOrderRepository.deleteAllHeader(orderNumber)
    }

    // delete details by order number
    fun deleteAllOrderDetailsItems(orderNumber: String) = viewModelScope.launch {
        startOrderRepository.deleteAllOrderItems(orderNumber)
    }

    // delete all tracing numbers by order number
    fun deleteAllTrackingNumbers(orderNumber: String) = viewModelScope.launch {
        startOrderRepository.deleteAllTrackingNumber(orderNumber)
    }

    // delete all Items Scanned by order number
    fun deleteAllItemsScanned(orderNumber: String) = viewModelScope.launch {
        startOrderRepository.deleteOrderItemsScanned(orderNumber)
    }

    // insert order header in local database
    fun insertOrderHeaderIntoDB(orderHeaderModule: OrderHeaderModule) = viewModelScope.launch {
        startOrderRepository.insertOrderHeader(orderHeaderModule)
    }

    // insert details in local database
    fun insertOrderDetails(orderDetails: OrderItemsDetails) = viewModelScope.launch {
        startOrderRepository.insertOrderDetails(orderDetails)
    }


    // get order data from Api
    fun getOrderData(orderNumber: String) = viewModelScope.launch {
        responseStateFlowFlow.value = DataStateFlow.Loading
        startOrderRepository.getOrderDataResponse(orderNumber)
            .catch { e ->
                responseStateFlowFlow.value = DataStateFlow.Failure(e)
            }.collect { order ->
                responseStateFlowFlow.value = DataStateFlow.GetOrderDataSuccess(order)
            }
    }


    // get header module by order number
    private val dataStateFlow: MutableStateFlow<DataStateFlow> =
        MutableStateFlow(DataStateFlow.Empty)
    val stateFlowData: StateFlow<DataStateFlow> = dataStateFlow

    fun getOrderByOrderNumber(orderNumber: String) = viewModelScope.launch {
        dataStateFlow.value = DataStateFlow.Loading
        startOrderRepository.getOrderHeader(orderNumber)
            .catch { e ->
                dataStateFlow.value = DataStateFlow.Failure(e)
            }.collect { value ->
                dataStateFlow.value = DataStateFlow.GetHeaderModuleSuccess(value)
            }
    }


    // get all headers to fill orders recycleView
    private val headersStateFlow: MutableStateFlow<DataStateFlow> =
        MutableStateFlow(DataStateFlow.Empty)
    val stateFlowHeaders: StateFlow<DataStateFlow> = headersStateFlow

    fun getAllOrderHeaders() = viewModelScope.launch {
        headersStateFlow.value = DataStateFlow.Loading
        startOrderRepository.getAllOrdersInDatabase()
            .catch { e ->
                headersStateFlow.value = DataStateFlow.Failure(e)
            }.collect { value ->
                headersStateFlow.value = DataStateFlow.GetHeaderModuleSuccess(value)
            }
    }


    // check if DB local contain any orders to edit
    private val existingOrdersStateFlow: MutableStateFlow<DataStateFlow> =
        MutableStateFlow(DataStateFlow.Empty)
    val stateFlowExistingOrders: StateFlow<DataStateFlow> = existingOrdersStateFlow

    fun getAllExistingOrders() = viewModelScope.launch {
        existingOrdersStateFlow.value = DataStateFlow.Loading
        startOrderRepository.getExistingOrders()
            .catch { e ->
                existingOrdersStateFlow.value = DataStateFlow.Failure(e)
            }.collect { value ->
                existingOrdersStateFlow.value = DataStateFlow.GetAllExistingOrders(value)
            }
    }


}