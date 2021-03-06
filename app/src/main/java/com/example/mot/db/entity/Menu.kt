package com.example.mot.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "menu")
data class Menu(
    @PrimaryKey var id : Long = -1,
    var category : Long? = -1,
    @Ignore var menuName: String="",
    @Ignore var langCode: Int = -1,
    var dicKor: String?="", //국문
    var dicChb: String?="", //중국어 간체
    var dicChg: String?="", //중국어 번체
    var dicEn: String?="",  //영문
    var dicJpe: String?="", //일문 음독
    var dicJph: String?="", //일문 훈독
    var price : Int = 0, //가격
    var spiciniess : Int? = 0, //맵기
    @Ignore var orderCnt : Int? = 1,
    @SerializedName("translatedText") var contents : String? = "", //설명
    var ingredients : String? = "" //주재료
)

