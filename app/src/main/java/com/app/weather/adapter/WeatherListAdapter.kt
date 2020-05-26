package com.app.weather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.app.weather.R
import com.app.weather.extension.loadImg
import com.app.weather.model.entity.WeatherEntity
import kotlinx.android.synthetic.main.weather_today_item.view.*

private const val VIEW_TYPE_TODAY = 1// today weather view type
private const val VIEW_TYPE_FORECASTS = 2// forecasts weather view type

class WeatherListAdapte() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemNameClickListener: ((Int) -> Unit)? = null


    /**
     * I Usually don't use entity for any adapter.
       I will use weatherEntity  inside this adapter because there
       is no need to create a new model which will consist by this entity
     * */
    private val weatherList: MutableList<WeatherEntity> = mutableListOf()


    private inner class ViewHolderNewItem internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private var timezoneAndDay: AppCompatTextView = itemView.timezoneAndDay
        private var mainTemperature: AppCompatTextView = itemView.mainTemperature
        private var temperatureInfo: AppCompatTextView = itemView.temperatureInfo
        private var sunInfo: AppCompatTextView = itemView.sunInfo
        private var humidity: AppCompatTextView = itemView.humidity
        private var description: AppCompatTextView = itemView.description
        private var weatherIcon: AppCompatImageView = itemView.weatherIcon

        @SuppressLint("SetTextI18n")
        internal fun bind(position: Int) {
            val item = weatherList[position]
            item?.let {
                timezoneAndDay.text = "${it.timezone}  ${it.day}"
                mainTemperature.text = "${it.tempDay}°C"
                temperatureInfo.text = "max = ${it.tempMax}°C \n" +
                        "min = ${it.tempMin}°C  \n" +
                        "Evening = ${it.tempEvening}°C\n" +
                        "Morning = ${it.tempMorning}°C"

                sunInfo.text = "sunrise ${it.sunrise} \n" +
                        "sunset ${it.sunset} "
                humidity.text = "humidity ${it.humidity} "
                description.text = "${it.description?.description}\n" +
                        "${it.description?.weatcherMain} "
                weatherIcon.loadImg(it.description?.weatherIcon)
            }
        }
    }

    private inner class ViewHolderDefault internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private var timezoneAndDay: AppCompatTextView = itemView.timezoneAndDay
        private var mainTemperature: AppCompatTextView = itemView.mainTemperature
        private var temperatureInfo: AppCompatTextView = itemView.temperatureInfo
        private var sunInfo: AppCompatTextView = itemView.sunInfo
        private var humidity: AppCompatTextView = itemView.humidity
        private var description: AppCompatTextView = itemView.description
        private var weatherIcon: AppCompatImageView = itemView.weatherIcon

        @SuppressLint("SetTextI18n")
        internal fun bind(position: Int) {
            val item = weatherList[position]
            item?.let {
                timezoneAndDay.text = "${it.timezone}  ${it.day}"
                mainTemperature.text = "${it.tempDay}°C"
                temperatureInfo.text = "max = ${it.tempMax}°C\n" +
                        "min = ${it.tempMin}°C  \n" +
                        "Evening = ${it.tempEvening}°C \n" +
                        "Morning = ${it.tempMorning}°C"

                sunInfo.text = "sunrise ${it.sunrise} \n" +
                        "sunset ${it.sunset} "
                humidity.text = "humidity ${it.humidity} "
                description.text = "${it.description?.description}\n" +
                        "${it.description?.weatcherMain} "
                weatherIcon.loadImg(it.description?.weatherIcon)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_TODAY) {
            ViewHolderNewItem(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.weather_today_item, parent, false)
            )
        } else ViewHolderDefault(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_forecasts_item_view, parent, false)
        ) //if it's not VIEW_TYPE_ONE then its VIEW_TYPE_TWO
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (!weatherList.isNullOrEmpty() && position == 0) {
            (holder as ViewHolderNewItem).bind(position)
        } else {
            (holder as ViewHolderDefault).bind(position)
        }
    }

    override fun getItemCount(): Int {
        return if (weatherList.isNullOrEmpty()) 0 else weatherList.size
    }

    fun isEmpty() = weatherList.isNullOrEmpty()

    public fun addAll(mutableList: List<WeatherEntity>) {
        this.weatherList.clear()
        this.weatherList.addAll(mutableList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_TODAY
        } else VIEW_TYPE_FORECASTS
    }


}