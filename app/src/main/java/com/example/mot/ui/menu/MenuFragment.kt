package com.example.mot.ui.menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mot.R
import com.example.mot.db.entity.Menu
import com.example.mot.extension.TAG
import com.example.mot.ui.ar.ARActivity
import com.example.mot.ui.base.BaseFragment
import com.example.mot.viewmodel.MenuViewModel
import kotlinx.android.synthetic.main.fragment_menu.*


/**
 * A simple [Fragment] subclass.
 */
class MenuFragment : BaseFragment() {

    private val mainAdapter: MainAdapter by lazy {
        MainAdapter { viewClickEventCallback(it) }
    }

    private val menuVM: MenuViewModel by lazy {
        ViewModelProvider(this,
            MenuViewModel.Factory(activity!!.application)
        ).get(MenuViewModel::class.java)
    }

    var catCode = -1
    var categoryCnt = -1
    var langCode = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        initRecyclerAdapter()
        getMenu()
        searchMenu()
        btnClickEventCallback()
    }
    private fun initRecyclerAdapter() {
        /*어댑터 생성후 어떤 데이터(arraylist)와 어떤 recyclerview를 쓸 것인지 설정*/
        recycler.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(activity?.application?.applicationContext)
            setHasFixedSize(true)
        }
    }

    private fun setLanguage(m: MutableList<Menu>) {
        when(langCode) {
            0-> m[0].langCode = 0
            1-> m[0].langCode = 1
            2-> m[0].langCode = 2
            3-> m[0].langCode = 3
        }
    }

    
    private fun getMenu() {
        for(i in 0 until categoryCnt) {
            when(catCode){
                0-> getAllMenu()
                i-> { getMenuByCategory(i.toLong()) }
                else -> { }
            }
        }
    }

    private fun getAllMenu() {
        menuVM.getAllMenu().observe(this, Observer<MutableList<Menu>> {
            it?.let {
                setLanguage(it)
                mainAdapter.setData(it)
            }
        })
    }

    private fun getMenuByCategory(cat: Long) {
        menuVM.getMenuByCategory(cat).observe(this, Observer<MutableList<Menu>>{
            it?.let {
                setLanguage(it)
                mainAdapter.setData(it)
            }
        })
    }

    private fun viewClickEventCallback(position: Int) {
        Intent(activity?.applicationContext, ARActivity::class.java).apply {
            Log.e(TAG, mainAdapter.getItem(position).id.toString())
            putExtra("menuId", mainAdapter.getItem(position).id)
            startActivity(this)
        }
    }

    private fun btnClickEventCallback() {
        mainAdapter.btnClickEvent
            .subscribe {map->
                val activity = activity as MainActivity
                val cnt = activity.order[map]
                if (activity.order[map] == null) activity.order[map] = 1
                cnt?.let { activity.order[map] = cnt+1 }
                activity.setCountText()
            }.apply { disposables.add(this) }
    }

    private fun searchMenu() {
        svMenu.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.e(TAG, query)
                return false
            }

            override fun onQueryTextChange(new: String?): Boolean {
                return false
            }
        })
    }
}
