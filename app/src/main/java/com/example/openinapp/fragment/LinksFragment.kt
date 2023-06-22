package com.example.openinapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openinapp.R
import com.example.openinapp.activity.Application
import com.example.openinapp.adapter.RvAdapter
import com.example.openinapp.models.RvModel
import com.example.openinapp.utils.NetworkResult
import com.example.openinapp.viewModel.ApiViewModel
import com.example.openinapp.viewModel.ApiViewModelFactory


class LinksFragment : Fragment() {

    private lateinit var apiViewModel: ApiViewModel
    private val list = ArrayList<RvModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_links, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        val apiRepository = (activity!!.applicationContext as Application).apiRepository
        apiViewModel = ViewModelProvider(this, ApiViewModelFactory(apiRepository))[ApiViewModel::class.java]
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)



        apiViewModel.apiResponse.observe(this, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    progressBar.visibility = View.GONE
                    list.add(RvModel(R.drawable.ic_todays_click, it.data!!.today_clicks.toString(), "Today's click"))
                    list.add(RvModel(R.drawable.ic_top_location, it.data.top_location.toString(), "Top Location"))
                    list.add(RvModel(R.drawable.ic_top_source, it.data.top_source, "Top Source"))
                    recyclerView.adapter = RvAdapter(activity!!.applicationContext, list)
                }
                is NetworkResult.Error -> {
                    Log.d("ERROR: ", it.message.toString())
                }
                else -> {
                    progressBar.visibility = View.VISIBLE
                }
            }
        })
        return view
    }
}