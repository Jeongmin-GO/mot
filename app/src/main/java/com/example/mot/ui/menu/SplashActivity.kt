package com.example.mot.ui.menu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.mot.R
import com.example.mot.ui.selectlanguage.SelectLanguageActivity

class SplashActivity :AppCompatActivity() {

    // This is the loading time of the splash screen
    private val SPLASH_TIME_OUT: Long = 3000

    override fun onCreate(savedInstanceState:Bundle?){

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this, SelectLanguageActivity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}