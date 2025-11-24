package com.example.coinapppdm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.coinapppdm.ui.articles.CoinDetailView
import com.example.coinapppdm.ui.articles.CoinListView
import com.example.coinapppdm.ui.theme.CoinAppPdmTheme
import com.example.coinapppdm.ui.articles.CoinListViewModel
import androidx.compose.runtime.getValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoinAppPdmTheme {
                Surface(color = MaterialTheme.colorScheme.background) {

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "list"
                    ) {
                        composable("list") {
                            CoinListView(navController = navController)
                        }

                        composable(
                            route = "coin/{id}",
                            arguments = listOf(
                                navArgument("id") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id") ?: return@composable

                            // 🔹 Usar o MESMO ViewModel da screen "list"
                            val parentEntry = remember(backStackEntry) {
                                navController.getBackStackEntry("list")
                            }
                            val listViewModel: CoinListViewModel = viewModel(parentEntry)
                            val uiState by listViewModel.uiState

                            // 🔹 Procurar a coin carregada no ViewModel
                            val coin = uiState.coins.firstOrNull { it.id == id }

                            if (coin != null) {
                                CoinDetailView(coin = coin)
                            } else {
                                Text("Coin não encontrada")
                            }
                        }
                    }
                }
            }
        }
    }
}