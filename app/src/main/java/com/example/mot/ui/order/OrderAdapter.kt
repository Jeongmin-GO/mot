package com.example.mot.ui.order

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mot.R
import com.example.mot.data.Order
import com.example.mot.db.entity.Menu
import com.example.mot.unit.extension.TAG
import com.example.mot.unit.extension.show
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChangeEvents
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.Holder>() {
    private val orders = mutableListOf<Menu>()
    private val totalPriceSubject = PublishSubject.create<Int>()
    var btnOrderTextChange = totalPriceSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    fun setData(newData: MutableList<Menu>) {
        newData.let {
            orders.clear()
            orders.addAll(newData)
            notifyDataSetChanged()
        }
    }

    var removedPosition : Int? = null

//    fun getRemovedItemPosition() : Int? {
//        var position = removedPosition
//        return position
//    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Log.e(TAG, orders[position].id.toString())
        orders[position].let { o ->
            when (orders[0].langCode) {
                0 -> holder.orderName.text = o.dicKor
                1 -> holder.orderName.text = o.dicEn
                2 -> holder.orderName.text = o.dicChb
                3 -> holder.orderName.text = o.dicJpe
            }
            holder.itemCount.text = o.orderCnt.toString()

            o.orderCnt?.let { cnt ->
                val plus = holder.btnPlus.clicks()
                    .map { +1 }
                val minus = holder.btnMinus.clicks()
                    .map { -1 }
                Observable.merge(plus, minus)
                    .scan(cnt) { p: Int, m: Int ->
                        if (p + m > 0) p + m
                        else 1
                    }
                    .subscribe { cnt ->
                        holder.itemView.show()
                        holder.itemCount.text = cnt.toString()
                    }
            }
            OrderActivity.orders.removeAt(position)

            holder.itemCount.textChanges()
                .map { it.toString().toInt() }
                .subscribe {
                    OrderActivity.orders[position].orderCount = it
                    btnOrderTextChange.onNext(it) }
            holder.bind(o)
        }


        holder.btnDelete.setOnClickListener(View.OnClickListener(){
            orders.removeAt(position)
//            removedPosition = position
            OrderActivity.orders.removeAt(position)
            println("<<<<<<<<<<<<<<<<<<<<<"+OrderActivity.orders+">>>>>>>>>>>>>>>>>")
            notifyDataSetChanged()
        })
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView!!) {
        private val orderNo: TextView = itemView.findViewById(R.id.txtOrderNo)
        val orderName: TextView = itemView.findViewById(R.id.txtOrderName)
        private val orderPrice: TextView = itemView.findViewById(R.id.txtOrderPrice)
        val itemCount: TextView = itemView.findViewById(R.id.count)
        val btnMinus: ImageButton = itemView.findViewById(R.id.btnminus)
        val btnPlus: ImageButton = itemView.findViewById(R.id.btnplus)
        val btnDelete : ImageButton = itemView.findViewById(R.id.btndelete)

        fun bind(data: Menu) {
            orderNo.text = "No." + data.id.toString()
            orderPrice.text = data.price.toString()
        }
    }
}