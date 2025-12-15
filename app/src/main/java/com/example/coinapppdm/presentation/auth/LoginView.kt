package com.example.coinapppdm.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.coinapppdm.presentation.theme.CoinAppPdmTheme
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.TextButton
import com.example.coinapppdm.presentation.viewmodel.LoginViewModel


@Composable
fun LoginView(
    navController : NavController = rememberNavController(),
    modifier: Modifier = Modifier
){
    val viewModel : LoginViewModel = viewModel()
    val uiState by viewModel.uiState

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        TextField(
            value = uiState.email ?: "",
            label = { Text("Email") },
            modifier = Modifier.padding(8.dp),
            onValueChange = {
                viewModel.updateEmail(it)
            })

        TextField(
            value = uiState.password ?: "",
            label = { Text("Password") },
            modifier = Modifier.padding(8.dp),
            onValueChange = {
                viewModel.updatePassword(it) },
            visualTransformation = PasswordVisualTransformation(), // 💡 Oculta o texto
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password) // ,
            )
        TextButton(onClick = {
            // Navegar para o ecrã de registo
            navController.navigate("signUp")
        }) {
            Text("Não tem conta? Registe-se.")
        }

        if (uiState.error != null) {
            Text(
                text = uiState.error!!,
                modifier = Modifier.padding(8.dp),
            )
        }

        Button(
            modifier = Modifier.padding(8.dp),
            onClick = {
                viewModel.login(){
                    navController.navigate("list")
                }
            }){
            Text("Login")
        }
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    CoinAppPdmTheme() {
        LoginView(

        )
    }
}