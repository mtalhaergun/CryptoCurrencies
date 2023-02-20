package com.example.cryptocurrencies.base

import com.example.cryptocurrencies.model.errorResponse.ErrorResponse
import com.example.cryptocurrencies.utils.NetworkResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T> safeApiRequest(
        apiRequest : suspend () -> T): NetworkResult<T>{
        return withContext(Dispatchers.IO){
            try{
                NetworkResult.Success(apiRequest.invoke())
            }catch (throwable: Throwable){
                when(throwable){
                    is HttpException -> {
                        NetworkResult.Error(false, errorBodyParser(throwable.response()?.errorBody()?.string()))
                    }
                    else -> NetworkResult.Error(true,throwable.localizedMessage)
                }
            }
        }
    }

}

private fun errorBodyParser(error : String?): String{
    error.let {
        return try{
            val errorResponse = Gson().fromJson(error,ErrorResponse::class.java)
            val errorMessage = errorResponse.status?.errorMessage
            errorMessage ?: "Bilinmeyen Bir Hata Oluştu"
        }catch (e: Exception){
            "Bilinmeyen Bir Hata Oluştu"
        }
    }
    return "Bilinmeyen Bir Hata Oluştu"
}