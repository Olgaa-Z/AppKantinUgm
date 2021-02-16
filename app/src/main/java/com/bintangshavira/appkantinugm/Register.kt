package com.bintangshavira.appkantinugm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bintangshavira.appkantinugm.retrofit.API
import com.bintangshavira.appkantinugm.retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    lateinit var myAPI: API
    var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val retrofit = RetrofitClient.instance
        myAPI = retrofit.create(API::class.java)

        textlogin.setOnClickListener(){
            startActivity(Intent(this, Login::class.java))
        }

        btn_register.setOnClickListener(){

            if (username.text.toString().isEmpty()) {
                username.setError("Masukkan Username")
                username.requestFocus()
                return@setOnClickListener
            }

            if (name.text.toString().isEmpty()) {
                name.setError("Masukkan Username")
                name.requestFocus()
                return@setOnClickListener
            }

            if (password.text.toString().isEmpty()) {
                password.setError("Masukkan Username")
                password.requestFocus()
                return@setOnClickListener
            }

            if (!confirmpassword.text.toString().equals(password.text.toString())) {
                confirmpassword.setError("Password tidak sama")
                confirmpassword.requestFocus()
                return@setOnClickListener
            }

            registerData(
                username.text.toString(),
                name.text.toString(),
                password.text.toString()
            )

        }
    }

    private fun registerData(username: String, name: String, password: String) {
        compositeDisposable.add(myAPI.registeruser(username, name,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { pesan ->
                Toast.makeText(this@Register, pesan, Toast.LENGTH_LONG).show()
            })
        Kosongkan_teks()
    }

    private fun Kosongkan_teks(){
        username.text.clear()
        password.text.clear()
        name.text.clear()
        email.text.clear()
        nik.text.clear()
        confirmpassword.text.clear()
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}