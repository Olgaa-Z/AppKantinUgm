package com.bintangshavira.appkantinugm

import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bintangshavira.appkantinugm.retrofit.Constant

class SplashScreen : AppCompatActivity() {

    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        handler = Handler()

        handler.postDelayed(Runnable {

            if (getSharedPreferences(Constant.PREFS_NAME, ContextWrapper.MODE_PRIVATE).contains(Constant.USERNAME)){
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                var intent = intent
                intent = Intent(this@SplashScreen, Login::class.java)
                startActivity(intent)
            }


            this@SplashScreen.finish()
        }, 3000)
    }
}