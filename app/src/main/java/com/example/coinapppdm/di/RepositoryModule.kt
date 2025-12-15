package com.example.coinapppdm.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.coinapppdm.data.repository.FirebaseNotesRepository
import com.example.coinapppdm.domain.repository.AlertsRepository
import com.example.coinapppdm.domain.repository.NotesRepository
import javax.inject.Singleton

/**
 * Módulo Hilt que liga interfaces de Domínio às implementações de Dados.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // Com @Binds, dizemos ao Hilt: "Quando alguém pedir o Contrato (AlertsRepository),
    // forneça a implementação (FirebaseAlertsRepository)."
    @Binds
    @Singleton
    abstract fun bindAlertsRepository(
        implementation: FirebaseNotesRepository
    ): NotesRepository

    // NOTA: Se você tiver outros repositórios (CoinRepository, FavoritesRepository),
    // eles seriam ligados aqui também!
}