package com.example.coinapppdm.models

data class Note(
    val id: String = "",
    val userId: String = "",
    val coinId: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis()
)