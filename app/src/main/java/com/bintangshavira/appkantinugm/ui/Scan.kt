package com.bintangshavira.appkantinugm.ui

import android.app.ProgressDialog
import android.content.ContextWrapper
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bintangshavira.appkantinugm.R
import com.bintangshavira.appkantinugm.retrofit.*
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_scan.*
import retrofit2.Call
import retrofit2.Response

class Scan : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_scan, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        btn_pay.setOnClickListener(){
            addTransaksi()
        }
    }


    private fun addTransaksi(){

        val jumlah_transfer = amount.text.toString()
        val scanusername = getscanusername.text.toString()
        val username = context?.getSharedPreferences(Constant.PREFS_NAME, ContextWrapper.MODE_PRIVATE)
                ?.getString(Constant.USERNAME, "not set")
        val saldo = context?.getSharedPreferences(Constant.PREFS_NAME, ContextWrapper.MODE_PRIVATE)
                ?.getString(Constant.SALDO, "not set")


        class Addtransaction : AsyncTask<Void, Void, String>(){

            lateinit var loading : ProgressDialog

            override fun onPreExecute() {
                super.onPreExecute()
                loading=  ProgressDialog.show(context, "menambahkan", "tunggu", false, false)
            }

            override fun doInBackground(vararg params: Void?): String {

                val params= HashMap<String, String?>()
                params ["username"]=username
                params ["jumlah_payment"]=jumlah_transfer
                params ["username_to"] = scanusername


                val rh = RequestHandler()
                Log.d("Params", params.entries.toString())
                return rh.sendPostRequest("http://192.168.18.38/Kantin/index.php/Transaksi_saldo/transaksi",params)
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                loading.dismiss()

            }

        }

        val aw = Addtransaction()
        aw.execute()

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


    //SCAN QR CODDE
    private fun initUI() {
        scanQRCode()
    }

    //SCAN QR CODDE
    private fun scanQRCode() {
        val integrator = IntentIntegrator.forSupportFragment(this).apply {
            captureActivity = CaptureActivity::class.java
            setOrientationLocked(false)
            setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            setPrompt("Scan Payment User ID")
        }
        integrator.initiateScan()
    }


    //SCAN QR CODDE
    // Get the results:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
            else Toast.makeText(context, "Hasil: " + result.contents, Toast.LENGTH_LONG).show()
            getscanusername.text=result.contents
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}