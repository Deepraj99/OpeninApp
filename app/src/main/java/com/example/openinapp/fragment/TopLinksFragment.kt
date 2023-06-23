package com.example.openinapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openinapp.R
import com.example.openinapp.activity.Application
import com.example.openinapp.adapter.TopLinksAdapter
import com.example.openinapp.utils.NetworkResult
import com.example.openinapp.viewModel.ApiViewModel
import com.example.openinapp.viewModel.ApiViewModelFactory

class TopLinksFragment : Fragment() {
    private lateinit var apiViewModel: ApiViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_top_links, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        val apiRepository = (activity!!.applicationContext as Application).apiRepository
        apiViewModel = ViewModelProvider(this, ApiViewModelFactory(apiRepository))[ApiViewModel::class.java]

        recyclerView.layoutManager = LinearLayoutManager(context)

        apiViewModel.apiResponse.observe(this, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    recyclerView.adapter = TopLinksAdapter(activity!!.applicationContext, it.data!!.data.top_links)
                }
                is NetworkResult.Error -> {
                    Log.d("ERROR: ", it.message.toString())
                }
                else -> {

                }
            }
        })

        return view
    }
}