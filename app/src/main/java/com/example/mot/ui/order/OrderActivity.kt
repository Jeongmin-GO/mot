package com.example.mot.ui.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mot.R
import com.example.mot.data.Order
import com.example.mot.db.entity.Menu
import com.example.mot.ui.base.BaseActivity
import com.example.mot.unit.Language
import com.example.mot.viewmodel.MenuViewModel
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : BaseActivity() {

    private val orderVM: MenuViewModel by lazy {
        ViewModelProvider(
            this,
            MenuViewModel.Factory(application)
        )
            .get(MenuViewModel::class.java)
    }

    private var menus: MutableList<Menu> = mutableListOf() //어댑터에 끼울 빈 리스트
    private val orderAdapter: OrderAdapter by lazy {
        OrderAdapter()
    }
    private var totalPrice: Int = 0
    lateinit var orderList : List<Order>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        getOrderData() // db에서 먼저 받아옴
        btnback.setOnClickListener {
            val intent = Intent()
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        btnorder.setOnClickListener {

        }
    }

    //db에서 가져오기
    private fun getOrderData() {
        cleansingData()
        for (i in orderList.indices) {
            orderVM.getMenuById(orderList[i].orderId).observe(this, Observer<Menu> {
                it.orderCnt = orderList[i].orderCount
                setOrderAdapterData(it)
            })
        }
        initOrderAdapter()
    }

    //중복제거
    private fun cleansingData() {
        for (i in orderItem.indices) {
            val tmp = orderItem[i]
            var cnt = 1
            repeat(orderItem.filter { order -> order.orderId == tmp.orderId }.size) { tmp.orderCount = cnt++ }
        }
        orderList = orderItem.distinct()
    }

    private fun setLanguage(m: MutableList<Menu>) {
        when (Language.langCode) {
            0 -> m[0].langCode = 0
            1 -> m[0].langCode = 1
            2 -> m[0].langCode = 2
            3 -> m[0].langCode = 3
        }
    }

    private fun setOrderAdapterData(menuList: Menu) {
        menus.let {
            it.add(menuList)
            setLanguage(it)
            orderAdapter.setData(it)
        }
    }

    private fun initOrderAdapter() {
        rvOrder.adapter = orderAdapter
        val lm = LinearLayoutManager(this)
        rvOrder.layoutManager = lm
        rvOrder.setHasFixedSize(true)
    }

    companion object {
        var orderItem = ArrayList<Order>()
    }

}