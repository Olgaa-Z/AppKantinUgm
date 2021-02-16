package com.bintangshavira.appkantinugm.ui

import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bintangshavira.appkantinugm.R
import com.bintangshavira.appkantinugm.retrofit.Constant
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import kotlinx.android.synthetic.main.activity_my_qr.*
import kotlinx.android.synthetic.main.activity_profil.*

class MyQr : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_qr)

        val user= this.getSharedPreferences(Constant.PREFS_NAME, ContextWrapper.MODE_PRIVATE)
            ?.getString(Constant.USERNAME, "not set")

        //Generated code id username
        val text=user.toString()
        if (text.isNotBlank()){
            val bitmap = generateQRCode(text)
            myqr_image.setImageBitmap(bitmap)
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