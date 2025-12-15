package com.example.coinapppdm.ui.articles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coinapppdm.ui.viewModel.CoinListState
import com.example.coinapppdm.ui.viewModel.CoinListViewModel
import com.example.coinapppdm.ui.viewModel.FavoritesViewModel

@Composable
fun CoinListView(
    modifier: Modifier = Modifier,
    navController : NavController,
    favoritesViewModel: FavoritesViewModel,
    viewModel: CoinListViewModel

){
    val uiState by viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.fetchCoins("eur")
    }

    CoinListViewContent(
        modifier = modifier,
        uiState = uiState,
        navController = navController,
        favoritesViewModel = favoritesViewModel
    )


}

@Composable
fun CoinListViewContent(
    modifier: Modifier = Modifier,
    uiState: CoinListState,
    navController: NavController,
    favoritesViewModel: FavoritesViewModel
){

    val favoriteIds = favoritesViewModel.favoriteIds

    Box(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)
        .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center){
        if (uiState.isLoading){
            CircularProgressIndicator()
        }else if(uiState.error != null){
            Text(uiState.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center)
        } else {
            if (uiState.coins.isEmpty()) {
                Text(
                    text = "Nenhuma coin carregada",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(
                    modifier = modifier.fillMaxSize()
                ) {
                    itemsIndexed(
                        items = uiState.coins,
                    ) { index, coin ->
                        val isFav = favoriteIds.contains(coin.id)
                        CoinListCell(
                            coin = coin,
                            isFavorite = isFav,
                            onClick = {
                                navController.navigate("coin/${coin.id}")
                            },
                            onToggleFavorite = { favoritesViewModel.toggleFavorite(coin) }
                        )
                    }
                }
            }
        }
    }
}
