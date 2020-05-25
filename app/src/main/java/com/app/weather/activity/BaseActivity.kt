package com.app.weather.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.weather.R
import com.app.weather.constant.AppTheme
import com.app.weather.extension.hide
import com.app.weather.extension.show
import com.app.weather.service.PreferenceService
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

open class BaseActivity : AppCompatActivity() {
    private val preferenceService: PreferenceService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkInterfaceMode()

    }

    public fun showLoading(showLoading:Boolean = true) {
        if (showLoading) {
            bottomLoadingView?.showShimmer(true)
            bottomLoadingView.show()
        }
    }

    public fun hideLoading() {
        bottomLoadingView?.stopShimmer()
        bottomLoadingView?.hideShimmer()
        bottomLoadingView.hide()

    }

    private fun checkInterfaceMode() {
        if (preferenceService.getInterfaceMode() == AppTheme.LIGHT) {
            setTheme(R.style.LightTheme)
            // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            setTheme(R.style.DarkTheme)
            //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }



}
