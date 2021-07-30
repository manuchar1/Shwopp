package com.tbcacademy.shwop.di

import com.tbcacademy.shwop.repositories.auth.AuthRepository
import com.tbcacademy.shwop.repositories.auth.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object AuthModule {

    @ActivityScoped
    @Provides
    fun provideAuthRepository() = AuthRepositoryImpl() as AuthRepository
}