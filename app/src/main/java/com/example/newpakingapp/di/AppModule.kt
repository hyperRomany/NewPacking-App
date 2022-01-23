package com.example.newpakingapp.di

import android.content.Context
import com.example.newpakingapp.data.api.ApiService
import com.example.newpakingapp.data.api.ApiServiceImpl
import com.example.newpakingapp.data.database.AppDatabase
import com.example.newpakingapp.data.database.DatabaseDao
import com.example.newpakingapp.data.database.DatabaseHelper
import com.example.newpakingapp.data.repository.HomeRepository
import com.example.newpakingapp.data.repository.LoginRepository
import com.example.newpakingapp.data.repository.StartOrderRepository
import com.example.newpakingapp.utlis.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesUrl() = BASE_URL

    @Provides
    @Singleton
    fun providesApiService(url:String) : ApiService =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)


    @Singleton
    @Provides
    fun provideApiRemoteDataSource(apiService: ApiService) = ApiServiceImpl(apiService)


    @Singleton
    @Provides
    fun provideDatabaseHelper(databaseDao: DatabaseDao) = DatabaseHelper(databaseDao)



    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)


    @Singleton
    @Provides
    fun provideDataDao(db: AppDatabase) = db.getDao()


    @Singleton
    @Provides
    fun provideLoginRepository(
        apiServiceImpl: ApiServiceImpl,
        databaseHelper: DatabaseHelper
    ) = LoginRepository(apiServiceImpl, databaseHelper)


    @Singleton
    @Provides
    fun provideStartOrderRepository(
        apiServiceImpl: ApiServiceImpl,
        databaseHelper: DatabaseHelper
    ) = StartOrderRepository(databaseHelper, apiServiceImpl)


    @Singleton
    @Provides
    fun provideHomeRepository(
        apiServiceImpl: ApiServiceImpl,
        databaseHelper: DatabaseHelper
    ) = HomeRepository(databaseHelper)

}