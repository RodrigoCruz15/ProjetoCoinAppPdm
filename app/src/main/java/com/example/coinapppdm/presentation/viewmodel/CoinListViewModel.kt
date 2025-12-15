package com.example.coinapppdm.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinapppdm.domain.model.Coin
import com.example.coinapppdm.data.repository.CoinRepository // 🔑 Novo Import
import dagger.hilt.android.lifecycle.HiltViewModel // 🔑 Novo Import
import kotlinx.coroutines.launch
import javax.inject.Inject // 🔑 Novo Import

data class CoinListState(
    val coins : List<Coin> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel // 🔑 HILT: Torna o ViewModel injetável
class CoinListViewModel @Inject constructor(
    // 🔑 HILT: Recebe o Repositório injetado
    private val repository: CoinRepository
): ViewModel() {

    private val _uiState = mutableStateOf(CoinListState())
    val uiState: State<CoinListState> = _uiState

    init {
        // Carrega automaticamente ao criar o ViewModel
        fetchCoins()
    }

    // 🔑 MUDANÇA CRÍTICA: Usa Coroutines para chamar a função suspensa
    fun fetchCoins(vsCurrency: String = "eur"){
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(isLoading = true, error = null)

            try {
                // 🔑 O Repositório faz o trabalho pesado
                val coins = repository.getCoins(vsCurrency)

                // Esta atualização é segura, pois está no viewModelScope
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    coins = coins
                )
            } catch (e: Exception) {
                // Trata exceções do Repositório (IOException, JSONException)
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Erro desconhecido ao carregar moedas"
                )
            }
        }
    }
}