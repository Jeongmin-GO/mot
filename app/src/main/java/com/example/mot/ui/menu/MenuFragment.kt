package com.example.mot.ui.menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mot.R
import com.example.mot.data.Order
import com.example.mot.db.entity.Menu
import com.example.mot.unit.extension.TAG
import com.example.mot.ui.ar.ARActivity
import com.example.mot.ui.base.BaseFragment
import com.example.mot.ui.order.OrderActivity
import com.example.mot.unit.Language
import com.example.mot.viewmodel.MenuViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_menu.*


/**
 * A simple [Fragment] subclass.
 */
class MenuFragment : BaseFragment() {

    private val menuAdapter: MenuAdapter by lazy {
        MenuAdapter { viewClickEventCallback(it) }
    }

    private val menuVM: MenuViewModel by lazy {
        ViewModelProvider(
            this,
            MenuViewModel.Factory(activity!!.application)
        ).get(MenuViewModel::class.java)
    }

    var catCode = -1
    var categoryCnt = -1

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
        txtAR.text = setInformText()
        btnClickEventCallback()
    }

    private fun setInformText(): String {
        return when (Language.langCode) {
            0 -> "메뉴를 클릭하시면 AR화면을 통해 음식을 미리보실 수 있습니다 "
            1 ->  "Click the menu to preview the food on the AR screen."
            2 -> "点击菜单即可通过AR画面提前看到食物"
            else -> "メニューをクリックすると、AR画面を通じて食べ物をプレビューできます。"
        }
    }

    private fun initRecyclerAdapter() {
        recycler.apply {
            adapter = menuAdapter
            layoutManager = LinearLayoutManager(activity?.application?.applicationContext)
            setHasFixedSize(true)
        }
    }

    private fun setLanguage(m: MutableList<Menu>) {
        when (Language.langCode) {
            0 -> m[0].langCode = 0
            1 -> m[0].langCode = 1
            2 -> m[0].langCode = 2
            3 -> m[0].langCode = 3
        }
    }

    private fun getMenu() {
        for (i in 0 until categoryCnt) {
            when (catCode) {
                0 -> getAllMenu()
                i -> getMenuByCategory(i.toLong())
                else -> {
                }
            }
        }
    }

    private fun getAllMenu() {
        menuVM.getAllMenu().observe(this, Observer<MutableList<Menu>> {
            it?.let {
                setLanguage(it)
                menuAdapter.setData(it)
            }
        })
    }

    private fun getMenuByCategory(cat: Long) {
        menuVM.getMenuByCategory(cat).observe(this, Observer<MutableList<Menu>> {
            it?.let {
                setLanguage(it)
                menuAdapter.setData(it)
            }
        })
    }

    private fun viewClickEventCallback(position: Int) {
        Intent(activity?.applicationContext, ARActivity::class.java).apply {
            putExtra("menuId", menuAdapter.getItem(position).id)
            startActivity(this)
        }
    }

    private fun btnClickEventCallback() {
        menuAdapter.btnClickEvent
            .subscribe {menu->
                OrderActivity.orders.add(Order(menu.id, menu.menuName, menu.price,1))
                val activity = activity as MenuActivity
                activity.setCountText()
            }
            .apply { disposables.add(this) }
    }

    private fun getMenuByName(query: String) {
        return when (Language.langCode) {
            0 -> {
                menuVM.getMenuByKor(query).observe(this, Observer<MutableList<Menu>> {
                    if (it.isNotEmpty()) {
                        setLanguage(it)
                        menuAdapter.setData(it)
                    } else {
                        getMenu()
                        Toast.makeText(
                            activity?.applicationContext,
                            "찾으시는 메뉴가 없습니다",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
            1 -> menuVM.getMenuByEng(query).observe(this, Observer<MutableList<Menu>> {
                if (it.isNotEmpty()) {
                    setLanguage(it)
                    menuAdapter.setData(it)
                } else {
                    getMenu()
                    Toast.makeText(
                        activity?.applicationContext,
                        "NO RESULT FOUND",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
            2 -> menuVM.getMenuByCha(query).observe(this, Observer<MutableList<Menu>> {
                if (it.isNotEmpty()) {
                    setLanguage(it)
                    menuAdapter.setData(it)
                } else {
                    getMenu()
                    Toast.makeText(activity?.applicationContext, "无结果", Toast.LENGTH_SHORT).show()
                }
            })
            else -> menuVM.getMenuByJp(query).observe(this, Observer<MutableList<Menu>> {
                if (it.isNotEmpty()) {
                    setLanguage(it)
                    menuAdapter.setData(it)
                } else {
                    getMenu()
                    Toast.makeText(activity?.applicationContext, "結果が見つかりません", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }

    private fun searchMenu() {
        svMenu.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getMenuByName(query)
                return false
            }

            override fun onQueryTextChange(new: String?): Boolean {
                if (new.isNullOrEmpty()) getMenu()
                return false
            }
        })
    }
}
