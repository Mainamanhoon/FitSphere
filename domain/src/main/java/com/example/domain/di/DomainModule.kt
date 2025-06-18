package com.example.domain.di

import com.example.domain.repository.youtubePlayerRepository
import com.example.domain.use_cases.ClearVideoProgressUseCase
import com.example.domain.use_cases.GetVideoProgressUseCase
import com.example.domain.use_cases.SaveVideoProgressUseCase
import com.example.domain.use_cases.StreamVideoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module

object DomainModule {

    @Provides
     fun providesSaveVideoProgressUseCase(repository: youtubePlayerRepository):SaveVideoProgressUseCase{
         return SaveVideoProgressUseCase(repository)
     }
    @Provides
    fun providesGetVideoProgressUseCase(repository: youtubePlayerRepository):GetVideoProgressUseCase{
        return GetVideoProgressUseCase(repository)
    }
    @Provides
    fun providesClearVideoProgressUseCase(repository: youtubePlayerRepository):ClearVideoProgressUseCase{
        return ClearVideoProgressUseCase(repository)
    }
    @Provides
    fun providesStreamVideoUseCase():StreamVideoUseCase{
        return StreamVideoUseCase()
    }
}