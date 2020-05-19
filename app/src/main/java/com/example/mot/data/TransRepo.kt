package com.example.mot.data

import com.google.gson.annotations.SerializedName

data class TransRepo(
    val response: Response
)

data class Response(
    val body: Body?,
    val header: Header
)

data class Header(
    @SerializedName("resultcode")val resultCode: Int,
    @SerializedName("resultmsg")val msg: String
)

data class Body(
    val items: Items?,
    @SerializedName("numofrows") val numOrRows: Int,
    @SerializedName("pageno")val pageNum: Int,
    @SerializedName("totalcount")val itemCnt: Int   //총 검색 결과 수

)