package com.app.weather.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.app.weather.R

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        val intent = Intent(applicationContext, MainActivity::class.java)
        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, 500)
    }
}
