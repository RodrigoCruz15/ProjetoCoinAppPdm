package com.example.coinapppdm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.coinapppdm.data.local.database.AppDatabase
import com.example.coinapppdm.ui.navigation.AppNavHost
import com.example.coinapppdm.ui.theme.CoinAppPdmTheme
import com.example.coinapppdm.ui.viewModel.CoinListViewModel
import com.example.coinapppdm.ui.viewModel.FavoritesViewModel
import com.example.coinapppdm.data.local.repository.FavoritesRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current
            val db = remember { AppDatabase.getInstance(context) }

            // Repositórios / ViewModels
            val favoritesRepository = remember {
                FavoritesRepository(db.favoriteDao())
            }

            val favoritesViewModel = remember {
                FavoritesViewModel(favoritesRepository)
            }
            LaunchedEffect(Unit) {
                favoritesViewModel.initForCurrentUser() // carrega os favoritos do user logado
            }


            val coinListViewModel = remember { CoinListViewModel() } // ou injeta repo real se tiveres
            val navController = rememberNavController()

            val auth = Firebase.auth
            val startDestination = if (auth.currentUser != null) "list" else "login"

            CoinAppPdmTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("CoinApp") },
                            actions = {
                                if (auth.currentUser != null) {
                                    TextButton(onClick = {
                                        auth.signOut()
                                        favoritesViewModel.resetForNewUser()
                                        navController.navigate("login") {
                                            popUpTo("list") { inclusive = true }
                                        }
                                    }) {
                                        Text("Sair")
                                    }
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        coinListViewModel = coinListViewModel,
                        favoritesViewModel = favoritesViewModel,
                        startDestination = startDestination,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
