package com.app.weather.service

import android.annotation.SuppressLint
import android.content.Context
import com.app.weather.constant.AppTheme


@SuppressLint("StaticFieldLeak")
class PreferenceService(private val context: Context) {

    @SuppressLint("CommitPrefEdits")
    fun saveInterfaceMode(interfaceMode: AppTheme = AppTheme.LIGHT):Boolean {
        val editor = context.getSharedPreferences("interfaceMode", Context.MODE_PRIVATE)?.edit()
        editor?.let {
            it.putString("interfaceModeKeyString", interfaceMode.name)
            return it.commit()
        }

        return false
    }

    fun getInterfaceMode(): AppTheme {
        val editor = context.getSharedPreferences("interfaceMode", Context.MODE_PRIVATE)
        val mode = editor.getString("interfaceModeKeyString", AppTheme.LIGHT.name)
        return if (mode == null) AppTheme.LIGHT else AppTheme.valueOf(mode)
    }


    fun saveOptionType(interval: Long = 1): Boolean? {
        val editor = context?.getSharedPreferences("interval", Context.MODE_PRIVATE)?.edit()
        editor?.putLong("interval", interval)
        return editor?.commit()
    }

    fun getOptionType(): Long {
        val editor = context?.getSharedPreferences("interval", Context.MODE_PRIVATE) ?: return 1
        return editor.getLong("interval", 1)
    }

}

