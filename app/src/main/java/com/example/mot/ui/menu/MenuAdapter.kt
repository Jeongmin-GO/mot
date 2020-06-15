package com.example.mot.ui.menu

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mot.R
import com.example.mot.databinding.ItemMenuBinding
import com.example.mot.db.entity.Menu
import com.example.mot.ui.base.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_menu.view.*

class MenuAdapter(private val viewClick: (position: Int) -> Unit): RecyclerView.Adapter<MenuAdapter.MainViewHolder>(){

    private val item = mutableListOf<Menu>()
    private val clickSubject = PublishSubject.create<Long>()
    val btnClickEvent: Observable<Long> = clickSubject

    //화면을 최초 로딩하여 만들어진 뷰가 없는 경우, xml파일을 inflate하여 뷰홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(parent, viewClick)

    //RecyclerView로 만들어지는 item의 총 개수 반환
    override fun getItemCount() = item.size

    //onCreateViewHolder에서 만든 view와 실제 입력되는 각각의 데이터를 연결한다.
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        item[position].let {data->
            when (item[0].langCode) {
                0 -> holder.itemView.tvMenuName.text = data.dicKor
                1 -> holder.itemView.tvMenuName.text = data.dicEn
                2 -> holder.itemView.tvMenuName.text = data.dicChb
                3 -> holder.itemView.tvMenuName.text = data.dicJpe
            }
            holder.bind(data)

            holder.itemView.btnPlus.setOnClickListener { clickSubject.onNext(data.id) }
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

    class MainViewHolder(parent: ViewGroup, viewClick: (position: Int) -> Unit) : BaseViewHolder<ItemMenuBinding>(
        R.layout.item_menu, parent) {
        init {
            itemView.setOnClickListener { viewClick(adapterPosition) }
        }

        fun bind (bindingItem: Menu){
            binding.itemMenuVM = bindingItem
        }
    }
}