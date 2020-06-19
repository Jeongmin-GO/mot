package com.example.mot.ui.menu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mot.R
import com.example.mot.db.entity.Category
import com.example.mot.ui.base.BaseActivity
import com.example.mot.ui.order.OrderActivity
import com.example.mot.viewmodel.CategoryViewModel
import com.google.android.material.tabs.TabLayout
import com.jakewharton.rxbinding2.view.clicks
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_button.view.*
import java.io.Serializable


class MenuActivity : BaseActivity() {

    private val categoryVM: CategoryViewModel by lazy {
        ViewModelProvider(this,
            CategoryViewModel.Factory(application)
        ).get(CategoryViewModel::class.java)
    }

    private lateinit var cat: MutableList<Category>
    private  val adapter = MenuPagerAdapter(supportFragmentManager)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

    }

    private fun init() {
        OrderActivity.orderItem.clear()
        getCategory()
        btnOrderClick()
    }

    private fun btnOrderClick() {
        btnOrderMenu.clicks()
            .subscribe {
                Intent(this, OrderActivity::class.java).apply {
                    startActivityForResult(this, MENU_REQUEST_CODE)
                } }
            .apply { disposables.add(this) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == MENU_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                OrderActivity.orderItem.clear()
                setCountText()
            }
        }
    }

    fun setCountText() {
        when(OrderActivity.orderItem.size) {
            0 -> tvMenuCnt.visibility = View.GONE
            else ->  {
                tvMenuCnt.visibility = View.VISIBLE
                tvMenuCnt.text = "${OrderActivity.orderItem.size}개"
            }
        }
    }

    //db에서 카테고리 가져오기
    private fun getCategory() {
        categoryVM.getMenuCategory().observe(this, Observer<MutableList<Category>>{
            it?.let {
                cat = it
                initViewPager()
            }
        })
    }

    private fun initViewPager() {
        setupViewPager()
        mviewPager.adapter = adapter // 뷰페이저에 adapter 장착
        layout_tab.setupWithViewPager(mviewPager) //탭레이아웃과 뷰페이저 연동
        setTabs(layout_tab)
    }

    private fun setupViewPager() {
        for(i in cat.indices) {
            val fv = MenuFragment()
            fv.categoryCnt = cat.size
            fv.catCode = i
            adapter.addItems(fv)
        }
    }

    private fun setTabs(tl: TabLayout) {
        for (i in cat.indices) {
            tl.getTabAt(i)?.customView = cat[i].name?.let { setTabText(it) }
            tl.getTabAt(i)?.tag = i
        }
    }

    private fun setTabText(tabName : String): View {
        val tabView = LayoutInflater.from(this).inflate(R.layout.tab_button, null)
        tabView.tab_text.text = tabName
        return tabView
    }

    companion object{
        const val MENU_REQUEST_CODE = 1000
    }
}
