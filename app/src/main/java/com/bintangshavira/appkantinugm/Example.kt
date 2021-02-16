package com.bintangshavira.appkantinugm

import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bintangshavira.appkantinugm.retrofit.Constant
import kotlinx.android.synthetic.main.activity_example.*

class Example : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        profilex.text = this.getSharedPreferences(Constant.PREFS_NAME, ContextWrapper.MODE_PRIVATE)
            ?.getString(Constant.USERNAME, "not set")

        nameex.text= this.getSharedPreferences(Constant.PREFS_NAME, ContextWrapper.MODE_PRIVATE)
            ?.getString(Constant.NAME, "not set")
    }
}