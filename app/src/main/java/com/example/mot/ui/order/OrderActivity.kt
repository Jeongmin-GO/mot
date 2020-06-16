package com.example.mot.ui.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mot.R
import com.example.mot.db.entity.Menu
import com.example.mot.extension.TAG
import com.example.mot.viewmodel.MenuViewModel
import kotlinx.android.synthetic.main.activity_ordermenu.*
import kotlin.collections.HashMap

class OrderActivity : AppCompatActivity() {

    private val orderVM: MenuViewModel by lazy {
        ViewModelProvider(
            this,
            MenuViewModel.Factory(application)
        )
            .get(MenuViewModel::class.java)
    }

    private lateinit var list: MutableList<Menu>
    private var order = hashMapOf<Long, Int>()

    var orders: MutableList<Menu> = mutableListOf() //어댑터에 끼울 빈 리스트
//    private val mAdapter = OrderAdapter(orders)

    private val orderAdapter: OrderAdapter by lazy {
        OrderAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordermenu)

        getOrderData() // db에서 먼저 받아옴
        Log.e("", order.keys.toString())

        btnback.setOnClickListener {
            val intent = Intent()
            intent.putExtra("ordersum", order)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    //db에서 가져오기
    private fun getOrderData() {
        order = intent.getSerializableExtra("order") as HashMap<Long, Int>
        Log.e(TAG, order.keys.toString())
        println("order의 키값 좀 보자 : "+ order.keys)
        for (i in order.keys) {
            orderVM.getMenuById(i).observe(this, Observer<Menu> {
                it.let {
                    cnt = order.getValue(i)
                    println(cnt)
                    orders.add(it)
                    println(">>>>>>>>>>>"+order.getValue(i))
                    orderAdapter.setData(orders)
                }
            })
        }
        initOrderData()
    }


    //리사이클러뷰에 어댑터 끼우기
    private fun initOrderData() {
        rvOrder.adapter = orderAdapter
        val lm = LinearLayoutManager(this)
        rvOrder.layoutManager = lm
        rvOrder.setHasFixedSize(true)
    }

    companion object {
        var cnt : Int = -1
    }
//    private fun setOrderData(itemView: View){
//        var orderNo = mAdapter.Holder(itemView).orderNo
//        for(i in order.values){
////           if(order.values.id == orderVM.getMenuById(list[i].id)
//            var idid = list[i].id
//            orderNo?.text = idid.toString()
//        }
//    }
//    private fun setOrderData(list: MutableList<Menu>) {
//        var key : Long = -1
//        for (key in order.)
//    }
}