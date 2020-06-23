package com.example.mot.ui.order

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.mot.R
import com.example.mot.ui.menu.MenuActivity

class FinishOrderActivity: AppCompatActivity(){
    private val SPLASH_TIME_OUT: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        Handler().postDelayed({
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}