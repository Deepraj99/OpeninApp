package com.example.openinapp.api

import com.example.openinapp.utils.Constant.Companion.API_KEY
import com.example.openinapp.utils.Constant.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    init {
        System.loadLibrary("keys")
    }
    private external fun getToken(): String
    private val token = getToken()
    fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("Authorization", "Bearer $token").build()
                chain.proceed(request)
            }.build())
            .build()
    }
}