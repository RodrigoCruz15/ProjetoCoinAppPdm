package com.example.coinapppdm.data.remote.repository

import com.example.coinapppdm.models.Note
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import java.util.UUID

class NotesRepository(
    private val firestore: FirebaseFirestore = Firebase.firestore,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    private val userId get() = auth.currentUser?.uid ?: ""

    private fun notesCollection() = firestore
        .collection("users")
        .document(userId)
        .collection("notes")

    suspend fun addNote(coinId: String, content: String) {
        if (userId.isEmpty()) return

        val note = Note(
            id = UUID.randomUUID().toString(),
            coinId = coinId,
            content = content,
            userId = userId,
            timestamp = System.currentTimeMillis()
        )

        notesCollection().document(note.id).set(note).await()
    }

    suspend fun getNotesForCoin(coinId: String): List<Note> {
        if (userId.isEmpty()) return emptyList()

        val snapshot = notesCollection()
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
        if (userId.isEmpty()) return
        notesCollection().document(noteId).delete().await()
    }
}

