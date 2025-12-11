package com.example.coinapppdm.data.repository

import com.example.coinapppdm.domain.model.Coin

interface CoinRepository {
    suspend fun getCoins(vsCurrency: String = "eur"): List<Coin>
}