package com.bintangshavira.appkantinugm

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.edit
import com.bintangshavira.appkantinugm.retrofit.API
import com.bintangshavira.appkantinugm.retrofit.Constant
import com.bintangshavira.appkantinugm.retrofit.ResponseLogin
import com.bintangshavira.appkantinugm.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Response

class Login : AppCompatActivity() {

    lateinit var myAPI: API
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtregister.setOnClickListener(){
            startActivity(Intent(this, Register::class.java))
        }

        preferences = getSharedPreferences(Constant.PREFS_NAME, Context.MODE_PRIVATE)

        val retrofit = RetrofitClient.instance
        myAPI = retrofit.create(API::class.java)

        preferences = getSharedPreferences(Constant.PREFS_NAME, Context.MODE_PRIVATE)

        btn_login.setOnClickListener {
            if (log_username.text.toString().isEmpty()) {
                log_username.error = "Masukkan Username"
                log_username.requestFocus()
                return@setOnClickListener
            } else if (log_password.text.toString().isEmpty()) {
                log_password.error = "Masukkan Password"
                log_password.requestFocus()
                return@setOnClickListener
            } else
                cekLogin(log_username.text.toString(), log_password.text.toString())
//                Kosongkan_teks()
        }
    }


    private fun cekLogin(username: String, password: String) {
        myAPI.loginUser(username, password)
            .enqueue(object : retrofit2.Callback<ResponseLogin> {

                override fun onFailure(call: retrofit2.Call<ResponseLogin>, t: Throwable) {
                    Toast.makeText(
                        this@Login,
                        "Username dan Password salah !",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(
                    call: retrofit2.Call<ResponseLogin>,
                    response: Response<ResponseLogin>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()?.data
                        preferences.edit(true) {
                            putString(Constant.NAME, data?.namaUser)
                            putString(Constant.PASSWORD, data?.passwordUser)
                            putString(Constant.USERNAME, data?.kodeUser)
                            putString(Constant.SALDO, data?.saldo)
                        }
                        val main = Intent(this@Login, MainActivity::class.java)
                        startActivity(main)
                    } else {
                        Toast.makeText(this@Login, response.message(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
    }
}