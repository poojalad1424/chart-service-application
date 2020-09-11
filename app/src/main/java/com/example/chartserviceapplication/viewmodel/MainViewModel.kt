package com.example.chartserviceapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chartserviceapplication.adapter.DataAdapter
import com.example.chartserviceapplication.interfaces.ItemClickListener
import com.example.chartserviceapplication.model.*
import com.example.chartserviceapplication.utils.ServiceBuilder
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel : ViewModel(), ItemClickListener {

    val compositeDisposable = CompositeDisposable()
    val adapterA: DataAdapter = DataAdapter(this)
    val adapterB: DataAdapter = DataAdapter(this)
    val adapterC: DataAdapter = DataAdapter(this)

    val dataList = MutableLiveData<List<Data>>()
    val feedList = MutableLiveData<List<FeedData>>()

    val view1Feed = MutableLiveData<FeedData>()
    val view2Feed = MutableLiveData<FeedData>()
    val view1Value = MutableLiveData<String>()
    val view2Value = MutableLiveData<String>()
    val view1Name = MutableLiveData<String>()
    val view2Name = MutableLiveData<String>()
    val view1Data = MutableLiveData<Data>()
    val view2Data = MutableLiveData<Data>()

    init {
        getData()
    }

    fun getData() {
        compositeDisposable.add(
                ServiceBuilder.buildService().getData()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ response -> onResponse(response) }, { t -> onFailure(t) }))

    }

    fun onFailure(t: Throwable) {
        Log.i("error", t.message)
    }

    fun onResponse(response: DataResponse) {
        dataList.value = response.Data
        callFeedApi(response.Data)
    }

    fun callFeedApi(dataList: List<Data>){
        val requests: ArrayList<Observable<FeedResponse>> = ArrayList()
        for (item: Data in dataList) {
            val itemlist = ArrayList<Data>()
            itemlist.add(item)
            requests.add(ServiceBuilder.buildService().getFeedData(FeedRequest(1, itemlist, 1, "/Date(0)/")).subscribeOn(Schedulers.io()))
        }
        Observable.zip(requests) { args -> Arrays.asList(args) }
                .subscribe({ res -> responseMethod(res)
                }, { t -> onFailure(t) })

    }

    override fun onItemlickListener(position: Int, data: Data, feedData: FeedData) {
        updateView(position, data, feedData)
    }

    fun responseMethod(it: MutableList<Array<Any>>) {
        val objectList = it[0]

        val itemlist = ArrayList<FeedData>()
        for (item: Any in objectList) {
            val feedResponse = item as FeedResponse
            itemlist.add(feedResponse.Data?.get(0))
        }
        feedList.postValue(itemlist)
    }

    fun updateView(postiion: Int, data: Data, feedData: FeedData) {
        if (postiion % 2 == 0) {
            view2Data.value = data
            view2Feed.value = feedData
            view2Name.value = data.ShortName
            view2Value.value = calculateChange(data, feedData)
        } else {
            view1Data.value = data
            view1Feed.value = feedData
            view1Name.value = data.ShortName
            view1Value.value = calculateChange(data, feedData)
        }
    }

    fun calculateChange(data: Data, feedData: FeedData) : String{
        val change = (((feedData.LastRate - data.LastTradePrice) / data.LastTradePrice) * 100).toInt()
        return "${data.LastTradePrice} ($change) %"
    }
}