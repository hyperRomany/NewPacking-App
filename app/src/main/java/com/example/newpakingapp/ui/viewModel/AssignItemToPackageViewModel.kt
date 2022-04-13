package com.example.newpakingapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newpakingapp.data.model.OrderDetailsItemsScanned
import com.example.newpakingapp.data.repository.AssignItemToPackageRepository
import com.example.newpakingapp.utlis.DataStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssignItemToPackageViewModel
@Inject constructor(private val assignItemToPackageRepository: AssignItemToPackageRepository) : ViewModel(){

    private val dataStateFlow: MutableStateFlow<DataStateFlow>
            = MutableStateFlow(DataStateFlow.Empty)
    val stateFlowData: StateFlow<DataStateFlow> = dataStateFlow

    fun getAllItemsInDetails(orderNumber : String) = viewModelScope.launch {
        dataStateFlow.value = DataStateFlow.Loading
        assignItemToPackageRepository.getAllItemsIOrder(orderNumber)
            .catch {
                dataStateFlow.value = DataStateFlow.Failure(it)
            }.collect {
                dataStateFlow.value = DataStateFlow.GetAllItemsInDetails(it)
            }
    }



    private val insertStateFlow: MutableStateFlow<DataStateFlow>
            = MutableStateFlow(DataStateFlow.Empty)
    val stateFlowInsert: StateFlow<DataStateFlow> = insertStateFlow

    fun insertScannedItem(orderDetailsItemsScanned: OrderDetailsItemsScanned) = viewModelScope.launch {
        insertStateFlow.value = DataStateFlow.Loading
        assignItemToPackageRepository.insertScannedItem(orderDetailsItemsScanned)
            .catch {
                insertStateFlow.value = DataStateFlow.Failure(it)
            }.collect {
                insertStateFlow.value = DataStateFlow.Success
            }
    }

}