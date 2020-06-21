package com.example.mot.ui.menu

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mot.R
import com.example.mot.databinding.ItemMenuBinding
import com.example.mot.db.entity.Menu
import com.example.mot.ui.base.BaseViewHolder
import com.example.mot.unit.extension.TAG
import com.example.mot.unit.extension.hide
import com.example.mot.unit.extension.show
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_menu.view.*

class MenuAdapter(private val viewClick: (position: Int) -> Unit) :
    RecyclerView.Adapter<MenuAdapter.MainViewHolder>() {

    private val item = mutableListOf<Menu>()
    private val clickSubject = PublishSubject.create<Menu>()
    val btnClickEvent: Observable<Menu> = clickSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(parent, viewClick)

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        item[position].let { data ->
            when (item[0].langCode) {
                0 -> holder.itemView.tvMenuName.text = data.dicKor
                1 -> holder.itemView.tvMenuName.text = data.dicEn
                2 -> holder.itemView.tvMenuName.text = data.dicChb
                3 -> holder.itemView.tvMenuName.text = data.dicJpe
            }

            holder.bind(data)

            holder.itemView.btnPlus.setOnClickListener {
                clickSubject.onNext(item[position])
            }
        }
    }

    fun setData(newData: MutableList<Menu>) {
        newData.let {
            item.clear()
            item.addAll(newData)
            notifyDataSetChanged()
        }
    }

    fun getItem(position: Int) = item[position]

    class MainViewHolder(parent: ViewGroup, viewClick: (position: Int) -> Unit) :
        BaseViewHolder<ItemMenuBinding>(
            R.layout.item_menu, parent
        ) {
        init {
            itemView.setOnClickListener { viewClick(adapterPosition) }
        }

        fun bind(bindingItem: Menu) {
            binding.itemMenuVM = bindingItem
        }
    }
}