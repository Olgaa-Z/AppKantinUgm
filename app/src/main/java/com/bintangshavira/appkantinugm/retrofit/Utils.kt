package com.bintangshavira.appkantinugm.retrofit

import java.text.NumberFormat
import java.util.*

object Utils {

    fun toRupiahFormat2(nominal: Int): String {
        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance(Locale.getDefault())

        return format.format(nominal)
    }
}