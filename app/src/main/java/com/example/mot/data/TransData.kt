package com.example.mot.data

data class Items(
    val item: MutableList<Item>?
)

data class Item(
    val cat1: String,   //대분류 : B (음식)
    val cat2: String,   //중분류 : B01 한식, B02:중식 B03 일식, B04 양식 B05 기타
    val cat3: String,   //소분류
    val dicChb: String, //중국어 간체
    val dicChg: String, //중국어 번체
    val dicEn: String,  //영문
    val dicJpe: String, //일문 음독
    val dicJph: String, //일문 훈독
    val dicKor: String //국문
)
