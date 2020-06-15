package com.example.mot.ui.menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mot.R
import com.example.mot.db.entity.Category
import com.example.mot.extension.TAG
import com.example.mot.ui.base.BaseActivity
import com.example.mot.ui.selectlanguage.SelectLanguageActivity
import com.example.mot.viewmodel.CategoryViewModel
import com.google.android.material.tabs.TabLayout
import com.jakewharton.rxbinding2.view.clicks
import com.kotlinpermissions.ifNotNullOrElse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_button.view.*


class MainActivity : BaseActivity() {

    private val categoryVM: CategoryViewModel by lazy {
        ViewModelProvider(this,
            CategoryViewModel.Factory(application)
        ).get(CategoryViewModel::class.java)
    }

    private lateinit var cat: MutableList<Category>
    private  val adapter = MenuPagerAdapter(supportFragmentManager)
    val order = mutableMapOf<Long, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        btnOrderMenu.setOnClickListener{
            val Intent = Intent(this, MenuOrder::class.java)
            startActivity(Intent)
        }
    }

    private fun init() {
        getCategory()
        btnOrderClick()
    }

    //주문하기 페이지 intent할 부분
    private fun btnOrderClick() {
        btnOrderMenu.clicks()
            .subscribe { Log.e(TAG, order.toString()) }
            .apply { disposables.add(this) }
    }

    fun setCountText() {
        when {
            sumOrder() == 0 -> tvMenuCnt.visibility = View.GONE
            else ->  {
                tvMenuCnt.visibility = View.VISIBLE
                tvMenuCnt.text = "${sumOrder()}개"
            }
        }
    }

    private fun sumOrder() : Int{
        var menuCnt = 0
        order.forEach { menuCnt += it.value }
        return menuCnt
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
            fv.langCode = intent.getIntExtra(SelectLanguageActivity.LANG_CODE,-1)
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

}
