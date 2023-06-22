package com.example.openinapp.api

import com.example.openinapp.models.ApiDataModel
import com.example.openinapp.utils.NetworkResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterface {
    @GET("dashboardNew")
    suspend fun getApiResponse(
    ) : Response<ApiDataModel>
}