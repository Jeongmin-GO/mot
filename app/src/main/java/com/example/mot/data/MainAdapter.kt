package com.example.mot.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mot.R

class MainAdapter(val context: Context, val list: ArrayList<Language>): RecyclerView.Adapter<MainAdapter.Holder>(){

    //화면을 최초 로딩하여 만들어진 뷰가 없는 경우, xml파일을 inflate하여 뷰홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return Holder(view)
    }

    //RecyclerView로 만들어지는 item의 총 개수 반환
    override fun getItemCount(): Int {
        return list.size
    }

    //onCreateViewHolder에서 만든 view와 실제 입력되는 각각의 데이터를 연결한다.
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(list[position], context)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val eng = itemView?.findViewById<TextView>(R.id.english)
        val jpn = itemView?.findViewById<TextView>(R.id.japanese)
        val chn = itemView?.findViewById<TextView>(R.id.chinese)

        fun bind (lang:Language, context: Context){
            eng?.text = lang.english
            jpn?.text = lang.japanese
            chn?.text = lang.chinese
        }
    }
}