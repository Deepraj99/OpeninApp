package com.example.openinapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openinapp.models.ApiDataModel
import com.example.openinapp.repository.ApiRepository
import com.example.openinapp.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApiViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            apiRepository.getApiResponse()
        }
    }

    val apiResponse: LiveData<NetworkResult<ApiDataModel>>
    get() = apiRepository.apiResponseLiveData
}