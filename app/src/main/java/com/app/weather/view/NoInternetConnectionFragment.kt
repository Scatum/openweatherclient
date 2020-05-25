package com.app.weather.view

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.app.weather.R
import com.app.weather.builder.AppBuilder
import com.app.weather.extension.onClick
import kotlinx.android.synthetic.main.fragment_no_internet_connection.*


class NoInternetConnectionFragment : BaseFragment() {


    override fun getLayoutId() = R.layout.fragment_no_internet_connection

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backBtn?.onClick {
            Navigation.findNavController(view).popBackStack()
        }

        enableNetworkBtn?.onClick {
            openWifiScreen()
        }
    }

    fun openWifiScreen() {
        startActivity(AppBuilder.buildWifiScreenIntent())
    }

}
