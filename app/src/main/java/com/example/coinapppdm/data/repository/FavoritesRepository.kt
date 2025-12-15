package com.example.coinapppdm.data.repository

import com.example.coinapppdm.data.local.dao.FavoriteCoinDao
import com.example.coinapppdm.data.local.entity.FavoriteCoinEntity
import com.example.coinapppdm.domain.model.Coin
import com.google.firebase.auth.FirebaseAuth

class FavoritesRepository (
    private val dao: FavoriteCoinDao,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
){
    private val userId: String
        get() = auth.currentUser?.uid ?: ""

    suspend fun getFavoriteIdsForCurrentUser(): Set<String>{
        val uid = userId
        if(uid.isEmpty()) return emptySet()

        val list = dao.getAllFavoritesByUser(uid)
        return list.map { it.id }.toSet()
    }

    suspend fun toggleFavorite(coin: Coin){
        val uid = userId
        if(uid.isEmpty()) return

        val isFav = dao.isFavorite(coin.id, userId)
        if(isFav){
            dao.removeFavorite(coin.id, userId)
        }else {
            dao.addFavorite(
                FavoriteCoinEntity(
                    id = coin.id,
                    name = coin.name,
                    symbol = coin.symbol,
                    image = coin.image,
                    userId = uid
                )
            )
        }
    }
}
