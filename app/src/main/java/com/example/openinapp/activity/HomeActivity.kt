package com.example.openinapp.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.openinapp.R
import com.example.openinapp.databinding.ActivityHomeBinding
import com.example.openinapp.utils.NetworkResult
import com.example.openinapp.viewModel.ApiViewModel
import com.example.openinapp.viewModel.ApiViewModelFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var apiViewModel: ApiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        val apiRepository = (application as Application).apiRepository
        apiViewModel = ViewModelProvider(this, ApiViewModelFactory(apiRepository))[ApiViewModel::class.java]

        NavigationUI.setupWithNavController(binding.bottomNavigationBar, findNavController(R.id.navController))

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