package com.example.coinapppdm.data.local.dao

import com.example.coinapppdm.data.local.entity.FavoriteCoinEntity
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface FavoriteCoinDao {

    @Query("SELECT * FROM favorites WHERE userId = :userId")
    suspend fun getAllFavoritesByUser(userId: String): List<FavoriteCoinEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(coin: FavoriteCoinEntity)

    @Query("DELETE FROM favorites WHERE id = :coinId AND userId = :userId")
    suspend fun removeFavorite(coinId: String, userId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :coinId AND userId = :userId)")
    suspend fun isFavorite(coinId: String, userId: String): Boolean

}