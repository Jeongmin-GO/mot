package com.example.mot.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mot.R
import com.example.mot.data.Item
import com.example.mot.data.Items
import com.example.mot.data.TransRepo
import com.example.mot.network.TransServiceApiProvider
import com.example.mot.network.TransServiceApiProvider.APP_NAME
import com.example.mot.network.TransServiceApiProvider.SERVICE_KEY
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_button.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var mContext : Context
    private lateinit var searchItem : MenuItem
    private lateinit var searchView : SearchView
    private lateinit var list: MutableList<Item>
    private val db = FirebaseFirestore.getInstance()

    private val mainAdapter: MainAdapter by lazy {
        MainAdapter { clickEventCallback(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mContext = applicationContext
        initViewPager()
        initRecyclerAdapter()
        getDataAllTest()
        getDataTest()
    }
    private fun createView(tabName : String): View {
        var tabView = LayoutInflater.from(mContext).inflate(R.layout.tab_button, null)

        tabView.tab_text.text = tabName
        return tabView
    }
    private fun initViewPager() {
        val koreaFood = FirstFragment()
        koreaFood.name="한식 프래그먼트"
        val usaFood = FirstFragment()
        usaFood.name="양식 프래그먼트"
        val chinaFood = FirstFragment()
        chinaFood.name="중식 프래그먼트"

        val adapter = MyPagerAdapter(supportFragmentManager)
        adapter.addItems(koreaFood)
        adapter.addItems(usaFood)
        adapter.addItems(chinaFood)

        mviewPager.adapter = adapter // 뷰페이저에 adapter 장착
        layout_tab.setupWithViewPager(mviewPager) //탭레이아웃과 뷰페이저 연동

        layout_tab.getTabAt(0)?.setCustomView(createView("한식"))
        layout_tab.getTabAt(1)?.setCustomView(createView("양식"))
        layout_tab.getTabAt(2)?.setCustomView(createView("중식"))
    }

    private fun getDataAllTest() {
        db.collection("/menu/category/gimbap")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("firebase", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("firebase", "Error getting documents.", exception)
            }
    }

    private fun getDataTest() {
        db.collection("/menu/category/gimbap").document("gimbap")
            .get()
            .addOnSuccessListener { document->
                if (document != null) {
                    val d = document.toObject(Item::class.java)
                    Log.d("firebase", "DocumentSnapshot data: ${d?.dicChb}")
                } else {
                    Log.d("firebase", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("firebase", "get failed with ", exception)
            }
        }


    private fun clickEventCallback(position: Int) {
    }


    private fun initRecyclerAdapter() {
        /*어댑터 생성후 어떤 데이터(arraylist)와 어떤 recyclerview를 쓸 것인지 설정*/
        recycler.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        searchItem = menu.findItem(R.id.app_bar_search)
        searchView = searchItem.actionView as SearchView

        searchView.maxWidth = Int.MAX_VALUE
        searchView.queryHint = "메뉴 이름을 검색해주세요"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                getTransWord(query)
                return false
            }

            override fun onQueryTextChange(new: String?): Boolean {
                return false
            }
        })
        return true
    }
    private fun getTransWord(w: String) {
        val getTransResponse : Call<TransRepo> = TransServiceApiProvider.getTransService().getTransWord(
            SERVICE_KEY,null,null,"AND", APP_NAME, "json","B", null,
            null,"KOR", w)

            getTransResponse.enqueue(object : Callback<TransRepo>{
                override fun onFailure(call: Call<TransRepo>, t: Throwable) {
                    Log.e("MainActivity", "Fail")
                }

                override fun onResponse(call: Call<TransRepo>, response: Response<TransRepo>) {
                    when(response.isSuccessful){
                        true -> {
                            Log.e("MainActivity", "Success")
                            list = response.body()?.response?.body?.items?.item!!
                            mainAdapter.setData(list)
                        }
                        false-> {}
                    }
                }
            })

    }
}
