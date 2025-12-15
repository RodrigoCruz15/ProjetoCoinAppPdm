package com.example.coinapppdm.data.repository

import androidx.compose.foundation.BasicTooltipState
import com.example.coinapppdm.domain.model.Coin
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import javax.inject.Inject // 🔑 Hilt Inject
import org.json.JSONArray
import okhttp3.OkHttpClient
import okhttp3.Request

class CoinRepositoryImp @Inject constructor(
    private val client: OkHttpClient = OkHttpClient(),
    private val BASE_URL: String = "https://api.coingecko.com/api/v3/"
): CoinRepository {

    override suspend fun getCoins(vsCurrency: String): List<Coin> =
        withContext(Dispatchers.IO){
            val endpoint = "coins/markets?vs_currency=$vsCurrency&order=market_cap_desc&per_page=20&page=1"
            val url = BASE_URL + endpoint

            val request = Request.Builder().url(url).build()

            client.newCall(request).execute().use { response ->

                if (!response.isSuccessful) {
                    throw IOException("Erro HTTP ${response.code} ao obter dados de moedas.")
                }

                val body = response.body?.string() ?: throw IOException("Resposta de rede vazia.")

                val jsonArray = JSONArray(body)
                val coinList = mutableListOf<Coin>()

                for (i in 0 until jsonArray.length()) {
                    val coinJson = jsonArray.getJSONObject(i)
                    // Note: Depende de Coin.fromJson funcionar corretamente
                    val coin = Coin.fromJson(coinJson)
                    coinList.add(coin)
                }

                return@withContext coinList
            }
        }
}

