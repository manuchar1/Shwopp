package com.tbcacademy.shwop.di

import com.tbcacademy.shwop.repositories.main.MainRepositoryImpl
import com.tbcacademy.shwop.repositories.main.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object MainModule {

    @ActivityScoped
    @Provides
    fun provideMainRepository() = MainRepositoryImpl() as MainRepository

}
