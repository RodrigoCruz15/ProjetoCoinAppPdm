package com.example.coinapppdm.data.local



import androidx.room.*
import com.example.coinapppdm.data.entity.FavoriteCoin
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCoinDao {

    // Observar favoritas em tempo real
    @Query("SELECT * FROM favorite_coins ORDER BY addedAt DESC")
    fun observeFavorites(): Flow<List<FavoriteCoin>>

    // Buscar todas as favoritas
    @Query("SELECT * FROM favorite_coins ORDER BY addedAt DESC")
    suspend fun getAllFavorites(): List<FavoriteCoin>

    // Verificar se é favorita
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_coins WHERE id = :coinId)")
    suspend fun isFavorite(coinId: String): Boolean

    // Adicionar favorita
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(coin: FavoriteCoin)

    // Remover favorita
    @Query("DELETE FROM favorite_coins WHERE id = :coinId")
    suspend fun removeFavorite(coinId: String)

    // Limpar todas as favoritas
    @Query("DELETE FROM favorite_coins")
    suspend fun clearAllFavorites()
}