package com.example.chartserviceapplication.model

data class Data(
        val Exch: String,
        val ExchType: String,
        val ScripCode: String,
        val ClientLoginType: Int,
        val RequestType: Int,
        val LastTradePrice: Double,
        val ShortName: String,
        val FullName: String,
        val Change: String
)
