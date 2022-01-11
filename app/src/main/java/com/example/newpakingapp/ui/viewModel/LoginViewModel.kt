package com.example.newpakingapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newpakingapp.data.model.Module
import com.example.newpakingapp.data.model.User
import com.example.newpakingapp.data.repository.LoginRepository
import com.example.newpakingapp.utlis.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class LoginViewModel
@Inject constructor(private val loginRepository: LoginRepository) : ViewModel(){


    private val apkStateFlow:MutableStateFlow<ApiState>
            = MutableStateFlow(ApiState.Empty)
    val stateFlowApk:StateFlow<ApiState> = apkStateFlow


    fun getVersion() = viewModelScope.launch {
        apkStateFlow.value = ApiState.Loading
        loginRepository.getVersion()
            .catch {  e ->
                apkStateFlow.value = ApiState.Failure(e)
            }.collect { version ->
                apkStateFlow.value = ApiState.Success(version)

            }
        }


    fun getLoginResponse(userLogin: HashMap<String, String>) = viewModelScope.launch {
        apkStateFlow.value = ApiState.Loading
        loginRepository.getLoginResponse(userLogin)
            .catch { e->
                apkStateFlow.value = ApiState.Failure(e)
            }.collect { login ->
                apkStateFlow.value = ApiState.SuccessLogin(login)

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