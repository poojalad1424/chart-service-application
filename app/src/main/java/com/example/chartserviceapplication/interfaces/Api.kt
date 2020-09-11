package com.example.chartserviceapplication.interfaces

import com.example.chartserviceapplication.model.DataResponse
import com.example.chartserviceapplication.model.FeedRequest
import com.example.chartserviceapplication.model.FeedResponse
import io.reactivex.Observable
import retrofit2.http.*

interface Api {
    @Headers("Ocp-Apim-Subscription-Key: 9038a799283f45aaad1ec6bf9b59051a")
    @GET("GetData")
    fun getData(): Observable<DataResponse>

    @Headers("Ocp-Apim-Subscription-Key: 9038a799283f45aaad1ec6bf9b59051a","Content-Type:application/json")
    @POST("MarketFeed")
    fun getFeedData(@Body bodyData : FeedRequest) : Observable<FeedResponse>
}