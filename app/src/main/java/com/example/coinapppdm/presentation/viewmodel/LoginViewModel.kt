package com.example.coinapppdm.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth

data class LoginState (
    var email : String? = null,
    var password : String? = null,
    var error : String? = null,
    var isLoading : Boolean = false
)

class LoginViewModel : ViewModel() {
    companion object {
        private const val TAG = "LoginViewModel"
    }
    private var auth: FirebaseAuth = Firebase.auth

    var uiState = mutableStateOf(LoginState())
        private set

    fun updateEmail(email : String) {
        uiState.value = uiState.value.copy(email = email)
    }

    fun updatePassword(password : String) {
        uiState.value = uiState.value.copy(password = password)
    }

    fun login(onLoginSuccess:()->Unit) {
        uiState.value = uiState.value.copy(isLoading = true)

        if (uiState.value.email.isNullOrEmpty()) {
            uiState.value = uiState.value.copy(
                isLoading = false,
                error = "Email is required")
            return
        }

        if (uiState.value.password.isNullOrEmpty()) {
            uiState.value = uiState.value.copy(
                isLoading = false,
                error = "Password is required")
            return
        }


        auth.signInWithEmailAndPassword(
            uiState.value.email!!,
            uiState.value.password!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    //updateUI(user)
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        error = null)
                    onLoginSuccess()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        error = "Wrong password or no internet connection")
                }
            }
    }
    fun signUp(onSignUpSuccess: () -> Unit) {
        uiState.value = uiState.value.copy(isLoading = true)

        // Validações (Repetir as validações de email/password)
        if (uiState.value.email.isNullOrEmpty() || uiState.value.password.isNullOrEmpty()) {
            uiState.value = uiState.value.copy(
                isLoading = false,
                error = "Email e Password são obrigatórios.")
            return
        }

        // Chamada à API para criar o utilizador
        auth.createUserWithEmailAndPassword(
            uiState.value.email!!,
            uiState.value.password!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // SUCESSO: Conta criada e autenticada
                    Log.d(TAG, "createUserWithEmail:success")
                    uiState.value = uiState.value.copy(isLoading = false, error = null)
                    onSignUpSuccess() // Navega para a lista principal
                } else {
                    // FALHA: Erro de email já em uso, password fraca, etc.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)

                    // Tratamento específico de erros (adaptar ao seu bloco when já existente)
                    val errorMessage = when (task.exception) {
                        is FirebaseAuthWeakPasswordException ->
                            "A password deve ter 6 ou mais caracteres."
                        is FirebaseAuthUserCollisionException ->
                            "Este email já está registado."
                        else ->
                            "Registo falhou. Tente novamente."
                    }

                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        error = errorMessage
                    )
                }
            }
    }

}

