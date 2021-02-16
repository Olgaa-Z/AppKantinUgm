package com.bintangshavira.appkantinugm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bintangshavira.appkantinugm.ui.Home
import com.bintangshavira.appkantinugm.ui.Profil
import com.bintangshavira.appkantinugm.ui.Scan
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.coordinatorLayout, Home())
            .commit()

        bottom.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.coordinatorLayout, Home())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_scan -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.coordinatorLayout, Scan())
                        .commit()
//                    intent = Intent(applicationContext, Help::class.java)
//                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_akun -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.coordinatorLayout, Profil())
                        .commit()
//                    intent = Intent(applicationContext, Example::class.java)
//                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }



            }
            return@setOnNavigationItemSelectedListener false
        }

    }

}
