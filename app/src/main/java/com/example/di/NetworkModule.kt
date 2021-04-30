package com.example.di

import com.example.data.api.UnsplashRemote
import com.example.data.api.UnsplashRemoteImpl
import com.example.data.api.UnsplashService
import com.example.data.api.UnsplashService.Companion.BASE_URL
import com.example.data.contextprovider.DispatcherProvider
import com.example.ui.imagelist.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get())}
    single { provideUnsplashService(get()) }
    single<UnsplashRemote> { provideSplashRemoteImpl(get(),get()) }
}

private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }
}

private fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideUnsplashService(retrofit: Retrofit): UnsplashService {
    return retrofit.create(UnsplashService::class.java)
}

private fun provideSplashRemoteImpl(
    service: UnsplashService,
    dispatcherProvider: DispatcherProvider
): UnsplashRemoteImpl {
    return UnsplashRemoteImpl(service, dispatcherProvider)
}