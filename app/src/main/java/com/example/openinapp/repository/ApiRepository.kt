package com.example.openinapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openinapp.api.ApiInterface
import com.example.openinapp.models.ApiDataModel
import com.example.openinapp.utils.Constant.Companion.API_KEY
import com.example.openinapp.utils.NetworkResult
import org.json.JSONObject

class ApiRepository(private val apiInterface: ApiInterface) {

    private val _apiResponseLiveData = MutableLiveData<NetworkResult<ApiDataModel>>()
    val apiResponseLiveData : LiveData<NetworkResult<ApiDataModel>>
    get() = _apiResponseLiveData

    suspend fun getApiResponse() {
        _apiResponseLiveData.postValue(NetworkResult.Loading())
        val apiResponse = apiInterface.getApiResponse()

        if (apiResponse.isSuccessful  &&  apiResponse.body() != null) {
            _apiResponseLiveData.postValue(NetworkResult.Success(apiResponse.body()!!))
        } else if (apiResponse.errorBody() != null) {
            val errorObj = JSONObject(apiResponse.errorBody()!!.charStream().readText())
            _apiResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
//            _apiResponseLiveData.postValue(NetworkResult.Error("Something went wrong!!"))
        } else {
            _apiResponseLiveData.postValue(NetworkResult.Error("Something went wrong!!"))
        }
    }

}