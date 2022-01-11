package com.example.newpakingapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.newpakingapp.data.model.Module
import com.example.newpakingapp.data.repository.HomeRepositories
import com.example.newpakingapp.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepositories) : ViewModel() {

    val allWords: LiveData<List<Module>> = repository.allModules.asLiveData()
}