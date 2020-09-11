package com.example.chartserviceapplication.interfaces

import com.example.chartserviceapplication.model.Data
import com.example.chartserviceapplication.model.FeedData

interface ItemClickListener {
    fun onItemlickListener(position: Int, data: Data, feedData: FeedData)
}