package com.example.mot.db.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey val id : Long = -1,
    var dicKor: String?="", //국문
    var dicChb: String?="", //중국어 간체
    var dicChg: String?="", //중국어 번체
    var dicEn: String?="",  //영문
    var dicJpe: String?="", //일문 음독
    var dicJph: String?="", //일문 훈독
    val name : String? = ""
)