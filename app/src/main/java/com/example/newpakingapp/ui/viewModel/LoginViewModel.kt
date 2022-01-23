package com.example.newpakingapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newpakingapp.data.model.Module
import com.example.newpakingapp.data.model.User
import com.example.newpakingapp.data.repository.LoginRepository
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
class LoginViewModel
@Inject constructor(private val loginRepository: LoginRepository) : ViewModel(){


    private val responseStateFlowFlow:MutableStateFlow<DataStateFlow>
            = MutableStateFlow(DataStateFlow.Empty)
    val stateFlowFlowResponse:StateFlow<DataStateFlow> = responseStateFlowFlow


    fun getVersion() = viewModelScope.launch {
        responseStateFlowFlow.value = DataStateFlow.Loading
        loginRepository.getVersion()
            .catch {  e ->
                responseStateFlowFlow.value = DataStateFlow.Failure(e)
            }.collect { version ->
                responseStateFlowFlow.value = DataStateFlow.VersionResponseSuccess(version)

            }
        }


    fun getLoginResponse(userLogin: HashMap<String, String>) = viewModelScope.launch {
        responseStateFlowFlow.value = DataStateFlow.Loading
        loginRepository.getLoginResponse(userLogin)
            .catch { e->
                responseStateFlowFlow.value = DataStateFlow.Failure(e)
            }.collect { login ->
                responseStateFlowFlow.value = DataStateFlow.LoginSuccessResponse(login)

            }
    }
    fun insertUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        loginRepository.insertUser(user)
    }

    fun insertModule(modules: List<Module>) = viewModelScope.launch(Dispatchers.IO) {

            loginRepository.insertModules(modules)
    }

    fun deleteAllUsers() = viewModelScope.launch(Dispatchers.IO) {
        loginRepository.deleteAllUsers()
    }

    fun deleteAllModules() = viewModelScope.launch(Dispatchers.IO) {
        loginRepository.deleteAllModules()
    }




}