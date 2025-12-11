package com.example.coinapppdm.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favorites")
data class FavoriteCoinEntity(
    @PrimaryKey val id: String,
    @PrimaryKey val userId: String,
    val name: String,
    val symbol: String,
    val image: String
)