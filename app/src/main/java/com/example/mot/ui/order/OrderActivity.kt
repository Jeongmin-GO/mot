package com.example.mot.ui.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mot.R
import com.example.mot.data.Order
import com.example.mot.db.entity.Menu
import com.example.mot.ui.base.BaseActivity
import com.example.mot.ui.order.OrderActivity.Companion.orderItem
import com.example.mot.unit.Language
import com.example.mot.unit.extension.TAG
import com.example.mot.viewmodel.MenuViewModel
import io.reactivex.schedulers.Schedulers
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
//    lateinit var orderList : List<Order>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        getOrderData() // db에서 먼저 받아옴
        btnback.setOnClickListener {
            orders.forEach {
                val tmp = it.orderCount
                it.orderCount = tmp }
            val intent = Intent()
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        orderAdapter.btnOrderTextChange.subscribe {
            btnorder.text = "${getTotalPrice()} 원 주문하기"
        }.apply { disposables.add(this) }

        btnorder.setOnClickListener {
            orders.clear()
            startActivity(Intent(this, FinishOrderActivity::class.java))
        }
    }

    private fun getTotalPrice(): Int {
        var totalPrice = 0
        orders.forEach {
            totalPrice += it.orderCount * it.orderPrice
        }
        return totalPrice
    }

    //db에서 가져오기
    private fun getOrderData() {
        cleansingData()
        Log.e(TAG, orderItem.toString())
        for (i in orderItem.indices) {
            orderVM.getMenuById(orderItem[i].orderId).observe(this, Observer<Menu> { db->
                orderItem.filter { order -> order.orderId == db.id }.forEach { orders.add(it)  }
                db.orderCnt = orderItem[i].orderCount
                setOrderAdapterData(db)
            })
        }
        initOrderAdapter()
    }

    //중복제거
    private fun cleansingData() {
        for (i in orders.indices) {
            val tmp = orders[i]

            var cnt = tmp.orderCount
            repeat(orders.filter { order -> order.orderId == tmp.orderId }.size) {
                tmp.orderCount = cnt++
            }
        }
        orderItem = orders.distinct().sortedBy { orders -> orders.orderId }
        orders.clear()
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
        var orders = ArrayList<Order>()
        lateinit var orderItem: List<Order>
    }

}
