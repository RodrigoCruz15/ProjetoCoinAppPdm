package com.example.coinapppdm.domain.repository

import com.example.coinapppdm.data.local.FavoriteCoinDao
import com.example.coinapppdm.data.entity.FavoriteCoin
import com.example.coinapppdm.data.remote.CoinApiService
import com.example.coinapppdm.data.remote.mapper.toDomain
import com.example.coinapppdm.domain.model.Coin
import com.example.coinapppdm.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CoinRepositoryImpl(
    private val apiService: CoinApiService,
    private val favoriteCoinDao: FavoriteCoinDao
) : CoinRepository {

    // API methods
    override suspend fun getCoins(): Result<List<Coin>> {
        return try {
            val dtos = apiService.getCoins()
            val coins = dtos.map { it.toDomain() }
            Result.success(coins)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCoinById(id: String): Result<Coin> {
        // implementação...
        TODO()
    }

    // Favorites methods ⭐
    override fun observeFavorites(): Flow<List<String>> {
        return favoriteCoinDao.observeFavorites().map { entities ->
            entities.map { it.id }
        }
    }

    override suspend fun getFavoriteIds(): List<String> {
        return favoriteCoinDao.getAllFavorites().map { it.id }
    }

    override suspend fun isFavorite(coinId: String): Boolean {
        return favoriteCoinDao.isFavorite(coinId)
    }

    override suspend fun addFavorite(coin: Coin) {
        val entity = FavoriteCoinEntity(
            id = coin.id,
            name = coin.name,
            symbol = coin.symbol
        )
        favoriteCoinDao.addFavorite(entity)
    }

    override suspend fun removeFavorite(coinId: String) {
        favoriteCoinDao.removeFavorite(coinId)
    }
}