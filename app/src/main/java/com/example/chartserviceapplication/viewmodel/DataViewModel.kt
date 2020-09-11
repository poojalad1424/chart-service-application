package com.example.chartserviceapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chartserviceapplication.model.Data
import com.example.chartserviceapplication.model.FeedData

class DataViewModel : ViewModel() {

    private val name = MutableLiveData<String>()
    private val description = MutableLiveData<Double>()
    private val change = MutableLiveData<String>()

    fun bind(data: Data, feedData: FeedData) {
        name.value = data.ShortName
        description.value = data.LastTradePrice
        change.value = "${((((feedData.LastRate - data.LastTradePrice) / data.LastTradePrice) * 100).toInt())} %"
    }

    fun getNameT(): MutableLiveData<String> {
        return name
    }

    fun getChange(): MutableLiveData<String> {
        return change
    }

    fun getDescriptionT(): MutableLiveData<Double> {
        return description
    }

}