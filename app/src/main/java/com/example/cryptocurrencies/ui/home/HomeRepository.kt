package com.example.cryptocurrencies.ui.home

import com.example.cryptocurrencies.base.BaseRepository
import com.example.cryptocurrencies.network.ApiFactory
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiFactory: ApiFactory): BaseRepository() {
    suspend fun getData(
        apiKey : String,
        limit : String
    ) = safeApiRequest {
        apiFactory.getData(apiKey,limit)
    }
}