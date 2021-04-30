package com.example.di

import com.example.data.api.UnsplashRemote
import com.example.data.database.UnsplashDao
import com.example.data.repository.UnsplashRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { providerUnsplashRepository(get(), get()) }
}

private fun providerUnsplashRepository(
    remote: UnsplashRemote,
    dao: UnsplashDao
): UnsplashRepository {
    return UnsplashRepository(remote, dao)
}