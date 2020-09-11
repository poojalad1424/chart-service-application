package com.example.chartserviceapplication.fragments

import android.os.Bundle
import android.provider.Settings.Global.putString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chartserviceapplication.R
import com.example.chartserviceapplication.databinding.TabTwoFragmentLayoutBinding
import com.example.chartserviceapplication.utils.TabType
import com.example.chartserviceapplication.viewmodel.MainViewModel

class TabBFragment : Fragment(){

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: TabTwoFragmentLayoutBinding = DataBindingUtil.inflate(inflater,
            R.layout.tab_two_fragment_layout, container, false)

        binding.viewModel = viewModel

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }
}
