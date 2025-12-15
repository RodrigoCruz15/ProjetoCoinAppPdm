package com.example.coinapppdm.ui.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinapppdm.data.remote.repository.NotesRepository
import com.example.coinapppdm.models.Note
import kotlinx.coroutines.launch


class NotesViewModel(
    private val repository: NotesRepository
) : ViewModel() {

    private val _notes = mutableStateOf<List<Note>>(emptyList())
    val notes: State<List<Note>> get() = _notes
    fun loadNotes(coinId: String) {
        viewModelScope.launch {
            _notes.value = repository.getNotesForCoin(coinId)
        }
    }

    fun addNote(coinId: String, content: String) {
        viewModelScope.launch {
            try {
                repository.addNote(coinId, content)
                loadNotes(coinId)
            } catch (e: Exception) {
                // Mostra mensagem de erro, loga ou alerta o utilizador
                Log.e("NotesViewModel", "Erro ao adicionar nota: ${e.message}")
            }
        }
    }

    fun deleteNote(noteId: String, coinId: String) {
        viewModelScope.launch {
            repository.deleteNote(noteId)
            loadNotes(coinId)
        }
    }
}