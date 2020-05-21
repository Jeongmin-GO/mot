package com.example.mot.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mot.R
import com.example.mot.data.Language
import com.example.mot.data.MainAdapter
import com.example.mot.data.TransRepo
import com.example.mot.network.TransServiceApiProvider
import com.example.mot.network.TransServiceApiProvider.APP_NAME
import com.example.mot.network.TransServiceApiProvider.SERVICE_KEY
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var list = arrayListOf<Language>(
        Language("sundae gukbap","スンデクッパ", "米肠汤饭"),
        Language("Pork cutlet", "とんかつ","炸猪排"),
        Language("Kimchi stew", "キムチチゲ","泡菜汤")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getTransWord()

        /*어댑터 생성후 어떤 데이터(arraylist)와 어떤 recyclerview를 쓸 것인지 설정*/
        val mAdapter = MainAdapter(this, list)
        recycler.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        recycler.layoutManager = lm
        recycler.setHasFixedSize(true)
    }

    private fun getTransWord() {
        val getTransResponse : Call<TransRepo> = TransServiceApiProvider.getTransService().getTransWord(
            SERVICE_KEY,null,null,"AND", APP_NAME, "json","B", null,
            null,"ENG", "rice")

            getTransResponse.enqueue(object : Callback<TransRepo>{
                override fun onFailure(call: Call<TransRepo>, t: Throwable) {
                    Log.e("MainActivity", "Fail")
                }

                override fun onResponse(call: Call<TransRepo>, response: Response<TransRepo>) {
                    when(response.isSuccessful){
                        true -> {
                            Log.e("MainActivity", "Success")
                            txtTest.text = response.body()?.response?.body?.items?.item?.get(0).toString()
                        }
                        false-> {}
                    }
                }
            })

    }
}
