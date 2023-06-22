package com.example.openinapp.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.openinapp.R
import com.example.openinapp.databinding.ActivityHomeBinding
import com.example.openinapp.utils.NetworkResult
import com.example.openinapp.viewModel.ApiViewModel
import com.example.openinapp.viewModel.ApiViewModelFactory

class HomeActivity : AppCompatActivity() {
//    init {
//        System.loadLibrary("keys")
//    }
//    external fun getToken(): String

    private lateinit var binding: ActivityHomeBinding
    private lateinit var apiViewModel: ApiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Log.d("API_KEY ", getToken())


        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        val apiRepository = (application as Application).apiRepository
        apiViewModel = ViewModelProvider(this, ApiViewModelFactory(apiRepository))[ApiViewModel::class.java]

        apiViewModel.apiResponse.observe(this, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    Log.d("RESPONSE: ", it.data.toString())
                }
                is NetworkResult.Error -> {
                    Log.d("ERROR: ", it.message.toString())
                }
                else -> {
                    Log.d("ERROR: ", "Something went wrong!")
                }
            }
        })
    }
}