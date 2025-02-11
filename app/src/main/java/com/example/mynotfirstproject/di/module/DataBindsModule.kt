package com.example.mynotfirstproject.di.module

import com.example.mynotfirstproject.data.datasource.db.implementations.LocalDataSourceImpl
import com.example.mynotfirstproject.data.datasource.db.implementations.RemoteDataSourceImpl
import com.example.mynotfirstproject.data.datasource.db.interfaces.LocalDataSource
import com.example.mynotfirstproject.data.datasource.db.interfaces.RemoteDataSource
import com.example.mynotfirstproject.data.jokeGenerator.JokeGenerator
import com.example.mynotfirstproject.data.jokeGenerator.JokeGeneratorImpl
import com.example.mynotfirstproject.data.repository.JokeRepository
import com.example.mynotfirstproject.domain.repository.JokesRepository
import dagger.Binds
import dagger.Module

@Module
interface DataBindsModule {

    @Binds
    fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    fun bindRepository(repositoryImpl: JokeRepository): JokesRepository
}