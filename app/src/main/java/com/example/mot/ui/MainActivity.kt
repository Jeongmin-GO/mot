package com.example.mot.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.mot.R
import com.example.mot.db.entity.Category
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_button.view.*


class MainActivity : AppCompatActivity() {

    private val categoryVM: CategoryViewModel by lazy {
        ViewModelProvider(this, CategoryViewModel.Factory(application)).get(CategoryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        getCategory()
    }

    private fun getCategory() {
        categoryVM.getMenuCategory().observe(this, Observer<MutableList<Category>>{
            it?.let { initViewPager(it) }
        })
    }
    private fun createView(tabName : String): View {
        val tabView = LayoutInflater.from(this).inflate(R.layout.tab_button, null)
        tabView.tab_text.text = tabName
        return tabView
    }

    private fun initViewPager(cat: MutableList<Category>) {
        val adapter = MenuPagerAdapter(supportFragmentManager)
        setupViewPager(mviewPager, adapter, cat)
        mviewPager.adapter = adapter // 뷰페이저에 adapter 장착
        layout_tab.setupWithViewPager(mviewPager) //탭레이아웃과 뷰페이저 연동
        setTabLayout(layout_tab,cat)
    }

    private fun setTabLayout(tl: TabLayout, cat: MutableList<Category>) {
        for (i in cat.indices) {
            tl.getTabAt(i)?.customView = cat[i].name?.let { createView(it) }
        }
    }
    private fun setupViewPager(vp : ViewPager, adapter: MenuPagerAdapter, cat: MutableList<Category>) {
        for(i in cat.indices) {
            val fv = MenuFragment()
            adapter.addItems(fv)
        }
    }

}
