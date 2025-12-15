package com.example.coinapppdm.data.local.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.example.coinapppdm.data.local.entity.FavoriteCoinEntity
import com.example.coinapppdm.data.local.dao.FavoriteDao
import com.example.coinapppdm.models.Coin

class FavoritesRepository(
    private val dao: FavoriteDao,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    private val userId: String
        get() = auth.currentUser?.uid ?: ""

    // FUNÇÃO QUE CARREGA OS FAVORITOS DO USER ATUAL
    suspend fun getFavoriteIdsForCurrentUser(): Set<String> {
        val uid = userId
        if (uid.isEmpty()) return emptySet()

        val list = dao.getFavoritesForUser(uid)
        return list.map { it.coinId }.toSet()
    }

    suspend fun toggleFavorite(coin: Coin) {
        val uid = userId
        Log.d("FavoritesRepository", "toggleFavorite: uid=$uid, coin=${coin.id}")
        if (uid.isEmpty()) return

        val isFav = dao.isFavorite(uid, coin.id )
        if (isFav) {
            dao.deleteFavorite(uid, coin.id)
        } else {
            dao.insertFavorite(
                FavoriteCoinEntity(
                    coinId = coin.id,
                    name = coin.name,
                    symbol = coin.symbol,
                    image = coin.image,
                    userId = uid
                )
            )
        }
    }
}