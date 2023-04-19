package com.example.youtube.di

import com.example.youtube.core.network.networkModule
import com.example.youtube.data.remote.remoteDataSource

val koinModules = listOf(
    repoModules,
    viewModules,
    remoteDataSource,
    networkModule
)