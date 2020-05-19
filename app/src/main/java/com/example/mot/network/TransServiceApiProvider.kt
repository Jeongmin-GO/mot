package com.example.mot.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLDecoder

private const val BASE_URL =
    "http://api.visitkorea.or.kr/openapi/service/rest/TransService/"
object TransServiceApiProvider {

    val SERVICE_KEY = URLDecoder.decode("CzSq1hrOhzU6gTxwHF%2FKA2GS7xO3gWTtqyT4k3qbSxwQ5tSKdCpk6Q2L0Lw0lUJ94EEq02ChjaY42FK7LHUPbA%3D%3D","utf-8")

    const val APP_NAME = "MOT"

    fun getTransService() : TransServiceApi = retrofit.create(TransServiceApi::class.java)
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
