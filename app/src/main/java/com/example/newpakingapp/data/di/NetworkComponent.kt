package com.example.newpakingapp.data.di

import com.example.newpakingapp.ui.view.LoginActivity
import com.example.newpakingapp.ui.viewModel.LoginViewModel
import dagger.Component


@Component
interface NetworkComponent {

    fun inject(activity: LoginActivity)

}