package com.example.mot.data

data class Order(
    val orderId : Long,
    val orderName : String,
    val orderPrice : Int,
    var orderCount : Int = 0
)