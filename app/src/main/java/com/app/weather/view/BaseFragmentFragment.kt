package com.app.weather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.app.weather.R
import com.app.weather.activity.MainActivity

abstract class BaseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)

    }

    abstract fun getLayoutId(): Int

    public fun showLoading(showLoading: Boolean = true) {
        (activity as MainActivity?)?.showLoading(showLoading)
    }

    public fun hideLoading() {
        (activity as MainActivity?)?.hideLoading()
    }

    fun navigateToNoInternetConnectionFragment() {
        view?.let {
            Navigation.findNavController(it).navigate(R.id.action_navigate_to_noInternetConnection)
        }
    }

}
