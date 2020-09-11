package com.example.chartserviceapplication

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.chartserviceapplication.adapter.PagerAdapter
import com.example.chartserviceapplication.fragments.TabAFragment
import com.example.chartserviceapplication.utils.TabType
import com.example.chartserviceapplication.viewmodel.MainViewModel
import com.example.chartserviceapplication.databinding.ActivityMainBinding
import com.example.chartserviceapplication.fragments.TabBFragment
import com.example.chartserviceapplication.fragments.TabCFragment
import com.example.chartserviceapplication.model.FeedData
import com.google.android.material.tabs.TabLayout
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit


class MainActivity : FragmentActivity() {

    private lateinit var subcription: Disposable
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var isInitialDataLoaded: Boolean = false
    private var focusedTab: TabType = TabType.TABA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.feedList.observe(this, Observer { value -> updateData(value) })

        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(TabAFragment(), TabType.TABA.name)
        adapter.addFragment(TabBFragment(), TabType.TABB.name)
        adapter.addFragment(TabCFragment(), TabType.TABC.name)

        binding.viewPager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewPager)

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                focusedTab = TabType.valueOf(tab.text.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()
        subcription = Observable.interval(0, 5,
                TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    if (viewModel.dataList.value != null && viewModel.dataList.value!!.size > 0) {
                        viewModel.callFeedApi(viewModel.dataList.value!!)
                    }
                }, {})
    }

    override fun onDestroy() {
        super.onDestroy()
        subcription?.dispose()
    }

    fun updateData(list: List<FeedData>) {
        if (!isInitialDataLoaded) {
            viewModel.adapterA.updateList(viewModel.dataList.value!!, list)
            viewModel.adapterB.updateList(viewModel.dataList.value!!, list)
            viewModel.adapterC.updateList(viewModel.dataList.value!!, list)
            isInitialDataLoaded = true
        } else {
            when (focusedTab) {
                TabType.TABA -> {
                    viewModel.adapterA.updateList(viewModel.dataList.value!!, list)
                }
                TabType.TABB -> {
                    viewModel.adapterB.updateList(viewModel.dataList.value!!, list)
                }
                TabType.TABC -> {
                    viewModel.adapterC.updateList(viewModel.dataList.value!!, list)
                }
            }
        }
    }
}
