package com.example.coinapppdm.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient // 🔑 Importar OkHttpClient
import java.util.concurrent.TimeUnit // 🔑 Importar para definir timeouts
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        // 🔑 Adicionar a lógica de construção do cliente OkHttp
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS) // Tempo limite para estabelecer conexão
            .readTimeout(15, TimeUnit.SECONDS)    // Tempo limite para ler dados
            .build()
    }
}