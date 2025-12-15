package com.example.coinapppdm.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.coinapppdm.data.repository.CoinRepositoryImp
import com.example.coinapppdm.data.repository.CoinRepository
import javax.inject.Singleton

/**
 * Módulo Hilt que liga interfaces de Domínio às implementações de Dados.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun bindCoinRepository(implementation: CoinRepositoryImp): CoinRepository
}

    // NOTA: Se você tiver outros repositórios (CoinRepository, FavoritesRepository),
    // eles seriam ligados aqui também!
}