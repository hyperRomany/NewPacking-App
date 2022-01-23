package com.example.newpakingapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newpakingapp.data.model.OrderHeaderModule
import com.example.newpakingapp.data.model.OrderItemsDetails
import com.example.newpakingapp.data.repository.StartOrderRepository
import com.example.newpakingapp.utlis.DataStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartOrderViewModel @Inject constructor(private val startOrderRepository: StartOrderRepository) : ViewModel() {

    private val responseStateFlowFlow: MutableStateFlow<DataStateFlow> =
        MutableStateFlow(DataStateFlow.Empty)
    val stateFlowFlowResponse: StateFlow<DataStateFlow> = responseStateFlowFlow


    fun deleteAllHeader(orderNumber: String) = viewModelScope.launch {
        startOrderRepository.deleteAllHeader(orderNumber)
    }

    fun deleteAllOrderDetailsItems(orderNumber: String) = viewModelScope.launch {
        startOrderRepository.deleteAllOrderItems(orderNumber)
    }

    fun deleteAllTrackingNumbers(orderNumber: String) = viewModelScope.launch {
        startOrderRepository.deleteAllTrackingNumber(orderNumber)
    }

    fun deleteAllItemsScanned(orderNumber: String) = viewModelScope.launch {
        startOrderRepository.deleteOrderItemsScanned(orderNumber)
    }

    fun insertOrderHeaderIntoDB(orderHeaderModule: OrderHeaderModule) = viewModelScope.launch {
        startOrderRepository.insertOrderHeader(orderHeaderModule)
    }

    fun insertOrderDetails(orderDetails: OrderItemsDetails) = viewModelScope.launch {
        startOrderRepository.insertOrderDetails(orderDetails)
    }

    fun getOrderData(orderNumber: String) = viewModelScope.launch {
        responseStateFlowFlow.value = DataStateFlow.Loading
        startOrderRepository.getOrderDataResponse(orderNumber)
            .catch { e ->
                responseStateFlowFlow.value = DataStateFlow.Failure(e)
            }.collect { order ->
                responseStateFlowFlow.value = DataStateFlow.GetOrderDataSuccess(order)
            }
    }


    private val dataStateFlow: MutableStateFlow<DataStateFlow> =
        MutableStateFlow(DataStateFlow.Empty)
    val stateFlowData: StateFlow<DataStateFlow> = dataStateFlow

    fun getOrderHeaders(orderNumber: String) = viewModelScope.launch {
        dataStateFlow.value = DataStateFlow.Loading
        startOrderRepository.getOrderHeader(orderNumber)
            .catch { e ->
                dataStateFlow.value = DataStateFlow.Failure(e)
            }.collect { value ->
                dataStateFlow.value = DataStateFlow.GetHeaderModuleSuccess(value)
            }
    }
}