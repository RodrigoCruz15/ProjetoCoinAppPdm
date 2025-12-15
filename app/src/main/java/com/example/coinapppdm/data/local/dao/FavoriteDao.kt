package com.example.coinapppdm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coinapppdm.data.local.entity.FavoriteCoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites WHERE userId = :userId")
    suspend fun getFavoritesForUser(userId: String): List<FavoriteCoinEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE userId = :userId AND coinId = :coinId)")
    suspend fun isFavorite(userId: String, coinId: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteCoinEntity)

    @Query("DELETE FROM favorites WHERE userId = :userId AND coinId = :coinId")
    suspend fun deleteFavorite(userId: String, coinId: String)
}