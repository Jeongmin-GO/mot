package com.example.mot.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mot.R
import com.example.mot.data.Item
import com.example.mot.data.Items
import com.example.mot.data.TransRepo
import com.example.mot.network.TransServiceApiProvider
import com.example.mot.network.TransServiceApiProvider.APP_NAME
import com.example.mot.network.TransServiceApiProvider.SERVICE_KEY
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var searchItem : MenuItem
    private lateinit var searchView : SearchView
    private lateinit var list: MutableList<Item>

    private val mainAdapter: MainAdapter by lazy {
        MainAdapter { clickEventCallback(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerAdapter()
        fTest()
    }

    private fun fTest() {
       val db = FirebaseFirestore.getInstance()
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
