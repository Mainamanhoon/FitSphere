package com.example.fitsphere.di


import com.example.fitsphere.data.service.StorageService
import com.example.fitsphere.data.impl.AccountServiceImpl
import com.example.fitsphere.data.impl.StorageServiceImpl
import com.example.fitsphere.data.service.AccountService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

class Module {
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
    fun provideFireStore():FirebaseFirestore{

        return FirebaseFirestore.getInstance()
    }
    @Provides
    fun provideStorageService(impl : StorageServiceImpl) : StorageService {

        return impl
    }
}