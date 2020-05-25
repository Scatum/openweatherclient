package com.app.weather.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.weather.R
import com.app.weather.adapter.WeatherListAdapte
import com.app.weather.constant.DataType
import com.app.weather.constant.LiveDataResponseStatus
import com.app.weather.model.entity.WeatherEntity
import com.app.weather.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_weather.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : BaseFragment() {

    private val weatherViewModel: WeatherViewModel by viewModel()
    private val weatherListAdapte: WeatherListAdapte = WeatherListAdapte()
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun getLayoutId() = R.layout.fragment_weather

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        initData()
    }

    private fun setupView() {
        linearLayoutManager = LinearLayoutManager(context)
        weatherListRV?.layoutManager = linearLayoutManager
        weatherListRV?.adapter = weatherListAdapte
    }

    private fun initData() {

        showLoading()
        weatherViewModel.getForecastsWeatherData()
            .observe(viewLifecycleOwner, Observer { triple ->
                headerTextView?.text = triple.first.name//to show from where getting data
                if (triple.first == DataType.UPDATED) {
                    setupUpdatedData(triple)
                } else {
                    setupOldData(triple)
                }
            })
    }

    private fun setupOldData(data: Triple<DataType, LiveDataResponseStatus, List<WeatherEntity>?>) {
        data.third?.let { weatherListAdapte.addAll(it) }
    }

    private fun setupUpdatedData(data: Triple<DataType, LiveDataResponseStatus, List<WeatherEntity>?>) {
        hideLoading()
        if (data.second == LiveDataResponseStatus.SUCCESS) {
            data.third?.let { weatherListAdapte.addAll(it) }//everything is okay just adding new data to the list
        } else if (data.second == LiveDataResponseStatus.NO_INTERNET_CONNECTION) {
            /**
             * there was a problem with network
             * */

            if (weatherListAdapte.isEmpty()) {
                // if local db is empty show NoInternetConnectionFragment
                navigateToNoInternetConnectionFragment()
            } else {
                //Weather ListAdapter has old data, we will show them
            }
        }


    }

    override fun onDestroyView() {
        Log.i("destroy", " onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.i("destroy", " onDestroy")
        super.onDestroy()
    }

}
