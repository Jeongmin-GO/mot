package com.example.mot.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLDecoder

private const val BASE_URL =
    "http://api.visitkorea.or.kr/openapi/service/rest/TransService/"
object TransServiceApiProvider {

    val SERVICE_KEY = URLDecoder.decode("dHjPgf%2FnIyPcLMiUOVqmRwMViDktYyv%2BioCI4ainTBGU09iVRzOcXWq6J5Gc59kfGTQcDcYMEXj8rhvboIeQcQ%3D%3D","utf-8")

    const val APP_NAME = "MOT"

    fun getTransService() : TransServiceApi = retrofit.create(TransServiceApi::class.java)
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
