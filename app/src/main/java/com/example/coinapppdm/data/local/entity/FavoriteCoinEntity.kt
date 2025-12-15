package com.example.coinapppdm.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "favorites",
    primaryKeys = ["coinId", "userId"]   // chave composta
)
data class FavoriteCoinEntity(
    val coinId: String,
    val name: String,
    val symbol: String,
    val image: String,
    val userId: String
)