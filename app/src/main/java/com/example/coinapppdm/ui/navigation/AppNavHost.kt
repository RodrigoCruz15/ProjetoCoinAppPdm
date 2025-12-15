package com.example.coinapppdm.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.coinapppdm.ui.articles.CoinDetailView
import com.example.coinapppdm.ui.articles.CoinListView
import com.example.coinapppdm.ui.auth.LoginView
import com.example.coinapppdm.ui.viewModel.CoinListViewModel
import com.example.coinapppdm.ui.viewModel.FavoritesViewModel
import androidx.compose.ui.Modifier

@Composable
fun AppNavHost(
    navController: NavHostController,
    coinListViewModel: CoinListViewModel,
    favoritesViewModel: FavoritesViewModel,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {

        composable("login") {
            LoginView(navController = navController)
        }

        composable("list") {
            CoinListView(
                navController = navController,
                viewModel = coinListViewModel,
                favoritesViewModel = favoritesViewModel
            )
        }

        composable(
            route = "coin/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            val coin = coinListViewModel.uiState.value.coins.firstOrNull { it.id == id }
            if (coin != null) {
                CoinDetailView(coin = coin)
            } else {
                Text("Coin não encontrada")
            }
        }
    }
}
