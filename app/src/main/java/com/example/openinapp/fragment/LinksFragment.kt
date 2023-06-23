package com.example.openinapp.fragment

import android.annotation.SuppressLint
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
import androidx.viewpager2.widget.ViewPager2
import com.example.openinapp.R
import com.example.openinapp.activity.Application
import com.example.openinapp.adapter.FragmentPageAdapter
import com.example.openinapp.adapter.RvAdapter
import com.example.openinapp.models.RvModel
import com.example.openinapp.utils.NetworkResult
import com.example.openinapp.viewModel.ApiViewModel
import com.example.openinapp.viewModel.ApiViewModelFactory
import com.google.android.material.tabs.TabLayout


class LinksFragment : Fragment() {

    private lateinit var apiViewModel: ApiViewModel
    private lateinit var fragmentPageAdapter: FragmentPageAdapter
    private val list = ArrayList<RvModel>()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_links, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val viewPager2 = view.findViewById<ViewPager2>(R.id.viewPager2)

        val apiRepository = (activity!!.applicationContext as Application).apiRepository
        apiViewModel = ViewModelProvider(this, ApiViewModelFactory(apiRepository))[ApiViewModel::class.java]
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        fragmentPageAdapter = FragmentPageAdapter(parentFragmentManager, lifecycle)

        tabLayout.addTab(tabLayout.newTab().setText("Top Links"))
        tabLayout.addTab(tabLayout.newTab().setText("Recent Links"))
        viewPager2.adapter = fragmentPageAdapter

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

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