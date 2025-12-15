package com.example.coinapppdm.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.example.coinapppdm.domain.model.CoinNote // Importar o modelo do Domínio
import com.example.coinapppdm.domain.repository.NotesRepository // Importar a Interface
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseNotesRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
): NotesRepository{
    private fun notesCollection() = db
        .collection("users")
        .document(auth.currentUser!!.uid)
        .collection("notes")

    override suspend fun getNote(coinId: String): CoinNote? {
        val snap = notesCollection().document(coinId).get().await()
        return if (snap.exists()) snap.toObject(CoinNote::class.java) else null
    }

    override suspend fun saveNote(coinId: String, text: String) {
        val note = mapOf(
            "coinId" to coinId,
            "note" to text,
            "updatedAt" to System.currentTimeMillis()
        )
        notesCollection()
            .document(coinId)
            .set(note, SetOptions.merge())
            .await()
    }
}