package com.example.cryptocurrencies.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencies.model.detail.DetailResponse
import com.example.cryptocurrencies.model.home.CryptoResponse
import com.example.cryptocurrencies.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository : DetailRepository) : ViewModel() {

    val detailResponse : MutableLiveData<DetailResponse?> = MutableLiveData()
    val isLoading : MutableLiveData<Boolean> = MutableLiveData(true)
    val onError : MutableLiveData<String?> = MutableLiveData()

    fun getDetail(
        apiKey : String,
        symbol : String
    ) = viewModelScope.launch {
        isLoading.value = true
        val request = repository.getDetail(apiKey,symbol)
        when(request){
            is NetworkResult.Success -> {
                detailResponse.value = request.data
                isLoading.value = false
            }
            is NetworkResult.Error -> {
                onError.value = request.message
                isLoading.value = false
            }
        }
    }
}