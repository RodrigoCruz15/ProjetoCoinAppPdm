package com.example.coinapppdm.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.coinapppdm.models.Coin
import androidx.compose.runtime.State
import com.example.coinapppdm.data.remote.repository.CoinRepositoryImpl

data class CoinListState(
    val coins : List<Coin> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)


class CoinListViewModel(
    private val coinRepository: CoinRepositoryImpl
) : ViewModel() {

    private val _uiState = mutableStateOf(CoinListState())
    val uiState: State<CoinListState> = _uiState

    fun fetchCoins(vsCurrency: String = "eur") {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        coinRepository.fetchCoins(vsCurrency) { coins, error ->
            if (coins != null) {
                _uiState.value = _uiState.value.copy(isLoading = false, coins = coins, error = null)
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = error)
            }
        }
    }
}
