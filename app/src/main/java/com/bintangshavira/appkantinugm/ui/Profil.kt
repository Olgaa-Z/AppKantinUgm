package com.bintangshavira.appkantinugm.ui

import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.bintangshavira.appkantinugm.Login
import com.bintangshavira.appkantinugm.R
import com.bintangshavira.appkantinugm.retrofit.Constant
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import kotlinx.android.synthetic.main.activity_profil.*



class Profil : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_profil, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usernameprofil.text=context?.getSharedPreferences(Constant.PREFS_NAME, ContextWrapper.MODE_PRIVATE)
            ?.getString(Constant.USERNAME, "not set")

        nameprofil.text = context?.getSharedPreferences(Constant.PREFS_NAME, ContextWrapper.MODE_PRIVATE)
            ?.getString(Constant.NAME, "not set")

        logout.setOnClickListener(){
            context?.getSharedPreferences(Constant.PREFS_NAME, ContextWrapper.MODE_PRIVATE)?.edit {
                clear()

            }
            startActivity(Intent(context, Login::class.java))
        }

        //Generated code id username
            val text=usernameprofil.text.toString()
            if (text.isNotBlank()){
                val bitmap = generateQRCode(text)
                imageView.setImageBitmap(bitmap)
            }
    }

    private  val TAG = "MainActivity"
    private fun generateQRCode(text: String): Bitmap {
        val width = 500
        val height = 500
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val codeWriter = MultiFormatWriter()
        try {
            val bitMatrix = codeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        } catch (e: WriterException) { Log.d(TAG, "generateQRCode: ${e.message}") }
        return bitmap
    }
}