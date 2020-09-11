package com.example.chartserviceapplication.model

data class FeedRequest(
        val Count: Int,
        val MarketFeedData: List<Data>,
        val ClientLoginType: Int,
        val LastRequestTime: String
)