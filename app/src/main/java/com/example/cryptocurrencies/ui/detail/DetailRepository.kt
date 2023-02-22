package com.example.cryptocurrencies.ui.detail

import com.example.cryptocurrencies.base.BaseRepository
import com.example.cryptocurrencies.network.ApiFactory
import javax.inject.Inject

class DetailRepository @Inject constructor(private val apiFactory: ApiFactory): BaseRepository() {

    suspend fun getDetail(
        apiKey : String,
        symbol : String
    ) = safeApiRequest {
        apiFactory.getDetail(apiKey,symbol)
    }
}