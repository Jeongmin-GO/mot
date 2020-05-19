package com.example.mot.network

import com.example.mot.data.TransRepo
import com.example.mot.network.TransServiceApiProvider.APP_NAME
import com.example.mot.network.TransServiceApiProvider.SERVICE_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TransServiceApi {

    @GET("transWord")
    fun getTransWord(
        @Query("serviceKey") serviceKey: String = SERVICE_KEY,
        @Query("numOfRows") numOfRows: Int?,
        @Query("pageNo") pageNo: Int?,
        @Query("MobileOS") moblieOS : String = "AND",
        @Query("MobileApp") appName : String = APP_NAME,
        @Query("_type") type: String = "json",
        @Query("cat1") cat1: String = "B",
        @Query("cat2") cat2: String? = null,
        @Query("cat3") cat3: String? = null,
        @Query("langSe") langSe : String = "ENG",
        @Query("word") word: String
    ) : Call<TransRepo>
}