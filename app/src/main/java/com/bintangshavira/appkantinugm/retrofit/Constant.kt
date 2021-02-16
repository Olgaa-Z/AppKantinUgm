package com.bintangshavira.appkantinugm.retrofit

import com.bintangshavira.appkantinugm.MainActivity

object Constant {

    //prefs key value
    const val NAME = "name"
    const val PASSWORD = "password"
    const val USERNAME = "username"
    const val EMAIL = "email"
    const val NIK = "nik"
    const val SALDO = "saldo"

    val PREFS_NAME = MainActivity::class.java.`package`?.toString()
}