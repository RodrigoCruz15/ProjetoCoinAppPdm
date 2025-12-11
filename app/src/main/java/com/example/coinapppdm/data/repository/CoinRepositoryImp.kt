package com.example.coinapppdm.data.repository

import com.example.coinapppdm.domain.model.Coin
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import org.json.JSONArray
import okhttp3.OkHttpClient
import okhttp3.Request

class CoinRepositoryImp (
    private val client: OkHttpClient = OkHttpClient()
): CoinRepository {

    override suspend fun getCoins(vsCurrency: String): List<Coin> =
        withContext(Dispatchers.IO){
            val url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=$vsCurrency&order=market_cap_desc&per_page=20&page=1"
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()

            if(!response.isSuccessful){
                throw IOException("HTTP ${response.code}")
            }
            val body = response.body?.string() ?: ""
            val jsonArray = JSONArray(body)
            val coinList = mutableListOf<Coin>()

            for (i in 0 until jsonArray.length()) {
                val coinJson = jsonArray.getJSONObject(i)
                val coin = Coin.fromJson(coinJson)
                coinList.add(coin)
            }

            coinList
        }
}

