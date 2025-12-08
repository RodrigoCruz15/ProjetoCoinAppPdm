package com.example.coinapppdm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_coins")
data class FavoriteCoin(
    // O ID da moeda da API será a chave primária
    @PrimaryKey
    val id: String,
    val symbol: String,
    val name: String,
    val imageUrl: String? // Para mostrar na lista de favoritos sem chamar a API
)