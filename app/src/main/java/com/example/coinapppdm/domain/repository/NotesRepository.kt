package com.example.coinapppdm.domain.repository

import com.example.coinapppdm.domain.model.Coin
import com.example.coinapppdm.domain.model.CoinNote
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

interface NotesRepository {
    suspend fun getNote(coinId: String): CoinNote?
    suspend fun saveNote(coinId: String, text: String)
}


