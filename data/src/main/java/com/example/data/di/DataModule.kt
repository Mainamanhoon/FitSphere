package com.example.data.di

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import com.example.data.repository.AccountServiceImpl
import com.example.data.repository.HealthServiceImpl
import com.example.data.repository.StorageServiceImpl
import com.example.domain.repository.AccountService
import com.example.domain.repository.HealthService
import com.example.domain.repository.StorageService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
    @Provides
    fun provideAuthRepository(impl : AccountServiceImpl) : AccountService {
        return impl
    }
    @Provides
    @Singleton
    fun provideFireStore(): FirebaseFirestore {

        return FirebaseFirestore.getInstance()
    }
    @Provides
    @Singleton
    fun provideStorageService(impl : StorageServiceImpl) : StorageService {
        return impl
    }
    @Provides
    @Singleton
    fun provideHealthService(impl:HealthServiceImpl):HealthService{
        return impl
    }

}