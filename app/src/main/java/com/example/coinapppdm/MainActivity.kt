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
import com.example.coinapppdm.presentation.ui.CoinDetailView
import com.example.coinapppdm.presentation.ui.CoinListView
import com.example.coinapppdm.presentation.theme.CoinAppPdmTheme
import com.example.coinapppdm.presentation.viewmodel.CoinListViewModel
import androidx.compose.runtime.getValue
//import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
//import com.example.coinapppdm.ui.auth.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.coinapppdm.presentation.auth.LoginView


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoinAppPdmTheme {
                Surface(color = MaterialTheme.colorScheme.background) {

                    val navController = rememberNavController()
                    val auth = Firebase.auth
                    val startDestination = if (auth.currentUser != null) "list" else "login"

                    NavHost(
                        navController = navController,
                        startDestination = startDestination
                    ) {
                        composable ("login"){
                            LoginView(
                                navController = navController
                            )
                        }
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