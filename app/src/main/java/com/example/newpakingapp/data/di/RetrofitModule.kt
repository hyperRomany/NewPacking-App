package com.example.newpakingapp.data.di

import com.example.newpakingapp.data.api.APiService
import com.example.newpakingapp.utlis.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class RetrofitModule {


    @Singleton
    @Provides
    fun getRetrofitInstance(retrofit: Retrofit):APiService{
        return retrofit.create(APiService::class.java)
    }


    @Singleton
    @Provides
    fun getRetrofitInstance() :Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}