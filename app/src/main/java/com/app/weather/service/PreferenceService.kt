package com.app.weather.service

import android.annotation.SuppressLint
import android.content.Context
import com.app.weather.constant.AppTheme


@SuppressLint("StaticFieldLeak")
class PreferenceService(private val context: Context) {

    fun saveInterfaceMode(interfaceMode: AppTheme = AppTheme.LIGHT) {
        val editor = context?.getSharedPreferences("interfaceMode", Context.MODE_PRIVATE)?.edit()
        editor?.putString("interfaceModeKeyString", interfaceMode.name)
        editor?.commit()
    }

    fun getInterfaceMode(): AppTheme? {
        val editor = context?.getSharedPreferences("interfaceMode", Context.MODE_PRIVATE)
        val mode = editor?.getString("interfaceModeKeyString", AppTheme.LIGHT.name)
        return mode?.let { AppTheme.valueOf(it) }
    }


    fun saveOptionType(interval: Long = 1) {
        val editor = context?.getSharedPreferences("interval", Context.MODE_PRIVATE)?.edit()
        editor?.putLong("interval", interval)
        editor?.commit()
    }

    fun getOptionType(): Long {
        val editor = context?.getSharedPreferences("interval", Context.MODE_PRIVATE) ?: return 1
        return editor.getLong("interval", 1)
    }

}

