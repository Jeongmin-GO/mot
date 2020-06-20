package com.example.mot.ui.menu

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mot.R
import com.example.mot.network.NaverApiResponse
import com.example.mot.ui.base.BaseActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_naverapi.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class NaverApiTestActivity : BaseActivity() {
    val api = NaverApi.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_naverapi)

        init()
    }

    private fun init(){
        btn.setOnClickListener {
            var translateWord = et_query.text.toString()
            if(translateWord != "") {
                val callPostTransferPapago = api.translatePapago(
                    "ko", "en", translateWord
                )

                callPostTransferPapago.enqueue(object : Callback<NaverApiResponse> {
                    override fun onResponse(
                        call: Call<NaverApiResponse>,
                        response: Response<NaverApiResponse>
                    ) {
                        tv_output.text = response.body()?.message?.result?.translatedText
                        Log.d("tag", "성공 : ${response.raw()}")
                    }

                    override fun onFailure(call: Call<NaverApiResponse>, t: Throwable) {
                        Log.d("tag", "실패 : $t")
                    }
                })
            }else {
                Toast.makeText(applicationContext, "번역할 단어가 없습니다. 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}