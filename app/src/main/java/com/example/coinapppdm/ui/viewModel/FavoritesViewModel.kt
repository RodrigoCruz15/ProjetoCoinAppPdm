package com.example.coinapppdm.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinapppdm.data.local.repository.FavoritesRepository
import com.example.coinapppdm.models.Coin
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: FavoritesRepository
) : ViewModel() {

    var favoriteIds by mutableStateOf<Set<String>>(emptySet())
        private set

    fun initForCurrentUser() {
        viewModelScope.launch {
            favoriteIds = repository.getFavoriteIdsForCurrentUser()
        }
    }

    fun resetForNewUser() {
        viewModelScope.launch {
            // limpa e depois CARREGA os favoritos do utilizador atual
            favoriteIds = repository.getFavoriteIdsForCurrentUser()
        }
    }

    fun toggleFavorite(coin: Coin) {
        viewModelScope.launch {
            repository.toggleFavorite(coin)
            // depois de mudar, recarrega a lista para ficar atualizada
            favoriteIds = repository.getFavoriteIdsForCurrentUser()
        }
    }

    fun isFavorite(cryptoId: String): Boolean {
        return favoriteIds.contains(cryptoId)
    }
}