package com.example.mot.ui.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mot.R
import com.example.mot.data.OrderItem

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.Holder>(){

    private val items: ArrayList<OrderItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ordermenu_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(items[position])
        var count = 1

        holder.btnMinus?.setOnClickListener{
            count--
            if (count <= 0) {
                count = 0
                holder.itemCount?.text = count.toString()
            }
            holder.itemCount?.text = count.toString()
        }

        holder.btnPlus?.setOnClickListener{
            count++
            holder.itemCount?.text = count.toString()
        }
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){
        val orderNo = itemView?.findViewById<TextView>(R.id.txtOrderNo)
        val orderName = itemView?.findViewById<TextView>(R.id.txtOrderName)
        val orderPrice = itemView?.findViewById<TextView>(R.id.txtOrderPrice)
        val itemCount = itemView?.findViewById<TextView>(R.id.count)
        val btnMinus = itemView?.findViewById<Button>(R.id.btnminus)
        val btnPlus = itemView?.findViewById<Button>(R.id.btnplus)

        fun bind(data:OrderItem){
            orderNo?.text = data.orderNo
            orderName?.text = data.orderName
            orderPrice?.text = data.orderPrice
            itemCount?.text = data.orderCount.toString()
        }
    }
}