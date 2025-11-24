package com.example.coinapppdm.models

import org.json.JSONObject
import java.net.URLEncoder
import java.net.URLDecoder

data class Coin (
    val id: String? = null,
    val symbol: String? = null,
    val name: String,
    val image: String? = null,
    val currentPrice: Double = 0.0,
    val priceChangePercentage24h: Double = 0.0,
){
    companion object {
        fun fromJson(json: JSONObject): Coin {
            return Coin(
                id = json.optString("id"),
                name = json.optString("name"),
                symbol = json.optString("symbol"),
                image = json.optString("image"),
                currentPrice = json.optDouble("current_price", 0.0),
                priceChangePercentage24h = json.optDouble("price_change_percentage_24h", 0.0),
            )
        }
    }

    fun toJson(): String {
        return JSONObject().apply {
            put("id", id)
            put("symbol", symbol)
            put("name", name)
            put("image", image)
            put("current_price", currentPrice)
            put("price_change_percentage_24h", priceChangePercentage24h)
        }.toString()
    }
}

fun String.encodeUrl(): String =
    URLEncoder.encode(this, "UTF-8")

fun String.decodeUrl(): String =
    URLDecoder.decode(this, "UTF-8")