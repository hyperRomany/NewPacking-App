package com.example.newpakingapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newpakingapp.data.repository.HomeRepository
import com.example.newpakingapp.utlis.DataStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel(){

   private val responseStateFlow: MutableStateFlow<DataStateFlow>
           = MutableStateFlow(DataStateFlow.Empty)
   val stateFlowFlowResponse: StateFlow<DataStateFlow> = responseStateFlow

   fun getAllModules() = viewModelScope.launch {
      responseStateFlow.value = DataStateFlow.Loading
      homeRepository.allModules()
         .catch {
            responseStateFlow.value = DataStateFlow.Failure(it)
         }.collect {
            responseStateFlow.value = DataStateFlow.GetAllModules(it)
         }
   }
}