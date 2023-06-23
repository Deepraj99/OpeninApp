package com.example.openinapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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
import java.util.*
import kotlin.collections.ArrayList


class LinksFragment : Fragment() {

    private lateinit var apiViewModel: ApiViewModel
    private lateinit var fragmentPageAdapter: FragmentPageAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var tvGreeting: TextView
    private val list = ArrayList<RvModel>()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_links, container, false)

        init(view)
        tabLayout()
        getLocalTime()

        apiViewModel.apiResponse.observe(this, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    progressBar.visibility = View.GONE
                    list.clear()
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

    @SuppressLint("SetTextI18n")
    private fun getLocalTime() {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)

        when (hour) {
            in 5..12 -> {
                tvGreeting.text = "Good morning"
            }
            in 13 downTo 5 -> {
                tvGreeting.text = "Good afternoon"
            }
            else -> {
                tvGreeting.text = "Good evening"
            }
        }
    }

    private fun tabLayout() {
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
    }
    private fun init(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager2 = view.findViewById(R.id.viewPager2)
        tvGreeting = view.findViewById(R.id.tvGreeting)

        val apiRepository = (activity!!.applicationContext as Application).apiRepository
        apiViewModel = ViewModelProvider(this, ApiViewModelFactory(apiRepository))[ApiViewModel::class.java]
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        fragmentPageAdapter = FragmentPageAdapter(parentFragmentManager, lifecycle)
    }
}