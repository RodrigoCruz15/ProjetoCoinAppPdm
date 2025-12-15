package com.example.coinapppdm.data.remote.repository

import com.example.coinapppdm.models.Coin
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Callback

import org.json.JSONArray
import java.io.IOException

class CoinRepositoryImpl {

    private val client = OkHttpClient()

    fun fetchCoins(vsCurrency: String = "eur", onResult: (List<Coin>?, String?) -> Unit) {
        val url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=$vsCurrency&order=market_cap_desc&per_page=20&page=1"
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onResult(null, e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        onResult(null, "Unexpected code $response")
                        return
                    }

                    val bodyString = response.body?.string()
                    if (bodyString.isNullOrBlank()) {
                        onResult(null, "Resposta vazia da API")
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
                        onResult(coinsList, null)
                    } catch (e: Exception) {
                        onResult(null, "Erro a processar dados: ${e.message}")
                    }
                }
            }
        })
    }
}

