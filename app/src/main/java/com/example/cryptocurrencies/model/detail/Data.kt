package com.example.cryptocurrencies.model.detail


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("BTC")
    val bTC: List<CoinDetail?>?
)