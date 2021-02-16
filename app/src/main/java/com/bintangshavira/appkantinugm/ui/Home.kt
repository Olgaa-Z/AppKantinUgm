package com.bintangshavira.appkantinugm.ui

import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bintangshavira.appkantinugm.R
import com.bintangshavira.appkantinugm.retrofit.API
import com.bintangshavira.appkantinugm.retrofit.Constant
import com.bintangshavira.appkantinugm.retrofit.ResponseSaldo
import com.bintangshavira.appkantinugm.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_home.*
import me.abhinay.input.CurrencySymbols
import retrofit2.Call
import retrofit2.Response


class Home : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_home, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        saldo.setCurrency(CurrencySymbols.INDONESIA)
//        saldo.setDecimals(false)
//        saldo.setDelimiter(true)
//        saldo.setSeparator(".")

        val user= context?.getSharedPreferences(Constant.PREFS_NAME, ContextWrapper.MODE_PRIVATE)
                ?.getString(Constant.USERNAME, "not set")
        getSaldo(user)

        //go to transfer balance

        cvtransfer.setOnClickListener(){
            startActivity(Intent(context, TransferBalance::class.java))
        }

        //go to my scan QR
        cv_myqr.setOnClickListener(){
            startActivity(Intent(context, MyQr::class.java))
        }

    }

    private fun getSaldo(key: String?) {
        val retrofit = RetrofitClient.instance
        val service = retrofit.create(API::class.java)
        service.getsaldo(key)

                .enqueue(object : retrofit2.Callback<ResponseSaldo>{
                    override fun onFailure(call: Call<ResponseSaldo>, t: Throwable) {
                        Toast.makeText(activity, t.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                            call: Call<ResponseSaldo>,
                            response: Response<ResponseSaldo>
                    ){
                        if (response.isSuccessful){
                            val vsaldo = response.body()?.balance
                            saldo.text= vsaldo.toString()
                        }
                    }

                })
    }
}