package com.example.skoolsaver.di

import com.example.skoolsaver.data.repository.JobsRepositoryImpl
import com.example.skoolsaver.domain.repository.JobsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
 abstract class RepositoryModule {

     @Singleton
     @Binds
     abstract fun bindsJobImpl(jobsRepositoryImpl: JobsRepositoryImpl): JobsRepository

}