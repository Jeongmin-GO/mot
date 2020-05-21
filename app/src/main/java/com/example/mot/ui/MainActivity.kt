package com.example.mot.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mot.R
import com.example.mot.data.TransRepo
import com.example.mot.network.TransServiceApiProvider
import com.example.mot.network.TransServiceApiProvider.APP_NAME
import com.example.mot.network.TransServiceApiProvider.SERVICE_KEY
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getTransWord()
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
