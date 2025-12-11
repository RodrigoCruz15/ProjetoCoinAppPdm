package com.example.coinapppdm.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.coinapppdm.domain.model.Coin
import androidx.compose.runtime.State
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

data class CoinListState(
    val coins : List<Coin> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)


class CoinListViewModel: ViewModel() {

    private val _uiState = mutableStateOf(
        CoinListState(
            isLoading = false,
            error = null,
            coins = emptyList()
        )
    )
    val uiState: State<CoinListState> = _uiState

    fun fetchCoins(vsCurrency: String = "eur"){

        _uiState.value = uiState.value.copy(isLoading = true, error = null)

        val url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=$vsCurrency&order=market_cap_desc&per_page=20&page=1"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if(!response.isSuccessful){
                        _uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = "Unexpected code $response"
                        )
                        return
                    }

                    val bodyString = response.body?.string()

                    if (bodyString.isNullOrBlank()) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = "Resposta vazia da API"
                        )
                        return
                    }

                    try {
                        val jsonArray = JSONArray(bodyString)
                        val coinsList = ArrayList<Coin>()

                        for (i in 0 until jsonArray.length()) {
                            val coinJson = jsonArray.getJSONObject(i)
                            val coin = Coin.fromJson(coinJson)
                            coinsList.add(coin)
                        }

                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            coins = coinsList,
                            error = null
                        )

                    } catch (e: Exception) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = "Erro a processar dados: ${e.message}"
                        )
                    }
                }
            }
        })
    }
}