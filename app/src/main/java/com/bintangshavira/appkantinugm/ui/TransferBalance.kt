package com.bintangshavira.appkantinugm.ui

import android.app.ProgressDialog
import android.content.ContextWrapper
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bintangshavira.appkantinugm.R
import com.bintangshavira.appkantinugm.retrofit.Constant
import com.bintangshavira.appkantinugm.retrofit.RequestHandler
import kotlinx.android.synthetic.main.activity_transfer_balance.*

class TransferBalance : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_balance)

        btn_send_transfer.setOnClickListener(){
            addTransfer()
        }
    }


    private fun addTransfer(){
        val get_username = edit_username_transfer.text.toString()
        val get_balance = edit_balance_transfer.text.toString()
        val username = this.getSharedPreferences(Constant.PREFS_NAME, ContextWrapper.MODE_PRIVATE)
            ?.getString(Constant.USERNAME, "not set")

        class transfer : AsyncTask<Void, Void, String>(){
            lateinit var loading : ProgressDialog
            override fun onPreExecute() {
                super.onPreExecute()

                loading=  ProgressDialog.show(this@TransferBalance, "menambahkan", "tunggu", false, false)
            }

            override fun doInBackground(vararg params: Void?): String {
                val params= HashMap<String, String?>()
                params ["username_to"]=get_username
                params ["jumlah_payment"]=get_balance
                params ["username"] = username


                val rh = RequestHandler()
                Log.d("Params", params.entries.toString())
                return rh.sendPostRequest("http://192.168.18.38/Kantin/index.php/Transaksi_saldo/transaksi",params)

            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                loading.dismiss()
            }

        }

        val aw = transfer()
        aw.execute()
    }
}