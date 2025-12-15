package com.example.coinapppdm.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel // 🔑 NOVO
import javax.inject.Inject // 🔑 NOVO
import com.example.coinapppdm.domain.repository.NotesRepository // 👈 Injeta a Interface
import kotlinx.coroutines.launch



data class CoinDetailUiState(
    val note: String = "",
    val error: String? = null,
    val saved: Boolean = false
)

class CoinDetailViewModel @Inject constructor(
    private val repo: NotesRepository
) : ViewModel(){

    var uiState by mutableStateOf(CoinDetailUiState())
        private set

    fun loadNote(coinId: String) {
        viewModelScope.launch {
            try {
                val existing = repo.getNote(coinId)
                uiState = uiState.copy(
                    note = existing?.note ?: "",
                    error = null,
                    saved = false
                )
            } catch (e: Exception) {
                val msg = e.message ?: ""

                if (msg.contains("client is offline", ignoreCase = true)) {
                    uiState = uiState.copy(
                        error = null,
                        saved = false
                    )
                } else {
                    uiState = uiState.copy(
                        error = msg,
                        saved = false
                    )
                }
            }
        }
    }
    fun onNoteChange(text: String) {
        uiState = uiState.copy(note = text, saved = false)
    }

    fun saveNote(cryptoId: String) {
        viewModelScope.launch {
            try {
                repo.saveNote(cryptoId, uiState.note)
                uiState = uiState.copy(
                    saved = true,
                    error = null
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    error = e.message ?: "Erro ao guardar nota",
                    saved = false
                )
            }
        }
    }
}

