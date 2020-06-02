package com.example.mot.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mot.R
import com.example.mot.data.Item
import com.example.mot.databinding.ItemMenuBinding
import com.example.mot.db.entity.Menu
import com.example.mot.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_menu.view.*

class MainAdapter(private val click: (position: Int) -> Unit): RecyclerView.Adapter<MainAdapter.MainViewHolder>(){

    private val item = mutableListOf<Menu>()

    //화면을 최초 로딩하여 만들어진 뷰가 없는 경우, xml파일을 inflate하여 뷰홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder = MainViewHolder(parent, click)

    //RecyclerView로 만들어지는 item의 총 개수 반환
    override fun getItemCount() = item.size

    //onCreateViewHolder에서 만든 view와 실제 입력되는 각각의 데이터를 연결한다.
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        item[position].let {
//            holder.itemView.tvMenuName.text = it.dicKor
            holder.bind(it)
        }
    }

    fun setData(newData: MutableList<Menu>) {
        newData.let {
            item.clear()
            item.addAll(newData)
            notifyDataSetChanged()
        }
    }

    class MainViewHolder(parent: ViewGroup, click: (position: Int) -> Unit) : BaseViewHolder<ItemMenuBinding>(
        R.layout.item_menu, parent) {
        init {
            itemView.setOnClickListener {  }
        }
        fun bind (bindingItem: Menu){
            binding.itemMenuVM = bindingItem
        }
    }
}