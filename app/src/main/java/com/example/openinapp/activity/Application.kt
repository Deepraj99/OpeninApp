package com.example.openinapp.activity

import android.app.Application
import com.example.openinapp.api.ApiInterface
import com.example.openinapp.api.RetrofitHelper
import com.example.openinapp.repository.ApiRepository

class Application : Application() {

    private lateinit var apiInterface: ApiInterface
    lateinit var apiRepository: ApiRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }
    private fun initialize() {
        apiInterface = RetrofitHelper.getInstance().create(ApiInterface::class.java)
        apiRepository = ApiRepository(apiInterface)

    }
}