package com.example.mot.ui.order

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mot.R
import com.example.mot.data.OrderItem
import kotlinx.android.synthetic.main.activity_ordermenu.*

class OrderActivity : AppCompatActivity() {
    var list = arrayListOf<OrderItem>(
        OrderItem("No.0001","제육덮밥","5000", 1),
        OrderItem("No.0003", "김치찌개","5500", 1)
    )

    private var order = mutableMapOf<Long, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordermenu)

        val mAdapter = OrderAdapter()
        orderItem.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        orderItem.layoutManager = lm
        orderItem.setHasFixedSize(true)

        getOrderData()
    }

    private fun getOrderData() {
        order = intent.getSerializableExtra("order") as MutableMap<Long, Int>
    }
}