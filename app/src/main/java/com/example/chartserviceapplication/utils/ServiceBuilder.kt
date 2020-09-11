package com.example.chartserviceapplication.utils

import com.example.chartserviceapplication.interfaces.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceBuilder {
  /*  private val client = OkHttpClient
        .Builder()
        .build()
*/
    val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    val client: OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
    }.readTimeout(2, TimeUnit.SECONDS)
            .connectTimeout(2, TimeUnit.SECONDS).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dataservice.iifl.in/ChartService/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(Api::class.java)

    fun buildService(): Api {
        return retrofit
    }
}