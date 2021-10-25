package com.elsawy.ahmed.news.data.provider

import android.content.Context
import android.content.SharedPreferences


object PreferencesHelper {

    private lateinit var prefs: SharedPreferences

    private const val PREFS_NAME = "params"
    private const val balance = "BALANCE"

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getBalance(): Double {
        return prefs.getString(balance, "0")?.toDouble()?: 0.0
    }

    fun setBalance(value: Double) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(balance, value.toString())
            commit()
        }
    }
}