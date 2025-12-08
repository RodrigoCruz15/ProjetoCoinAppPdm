package com.example.coinapppdm.domain.repository

import com.example.coinapppdm.domain.models.Coin
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    // Coins da API
    suspend fun getCoins(): Result<List<Coin>>
    suspend fun getCoinById(id: String): Result<Coin>

    fun observeFavorites(): Flow<List<String>>  // IDs das favoritas
    suspend fun getFavoriteIds(): List<String>
    suspend fun isFavorite(coinId: String): Boolean
    suspend fun addFavorite(coin: Coin)
    suspend fun removeFavorite(coinId: String)
}