package com.example.coinapppdm.data.remote.repository

import com.example.coinapppdm.models.Note
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class NotesRepository(
    private val firestore: FirebaseFirestore = Firebase.firestore,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    private val userId get() = auth.currentUser?.uid ?: ""

    suspend fun addNote(coinId: String, content: String) {
        if (userId.isEmpty()) return

        val note = hashMapOf(
            "userId" to userId,
            "coinId" to coinId,
            "content" to content,
            "timestamp" to System.currentTimeMillis()
        )

        firestore.collection("notes").add(note).await()
    }

    suspend fun getNotesForCoin(coinId: String): List<Note> {
        if (userId.isEmpty()) return emptyList()

        val snapshot = firestore.collection("notes")
            .whereEqualTo("userId", userId)
            .whereEqualTo("coinId", coinId)
            .get()
            .await()

        return snapshot.documents.map { doc ->
            Note(
                id = doc.id,
                userId = doc.getString("userId") ?: "",
                coinId = doc.getString("coinId") ?: "",
                content = doc.getString("content") ?: "",
                timestamp = doc.getLong("timestamp") ?: 0
            )
        }
    }

    suspend fun deleteNote(noteId: String) {
        firestore.collection("notes").document(noteId).delete().await()
    }
}
