package com.app.weather.activity

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.app.weather.R
import com.app.weather.extension.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            try {
                // safe call
                // need for bug navigationcontroller
                setContentView(R.layout.activity_main)
                setupNavController()
            } catch (ex: Exception) {
                Log.e("Exception", " onSetContentView ${ex.message}")
            }
        }
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setupNavController()
    }


    override fun onDestroy() {
        finish() // need for bug navigationcontroller
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    private fun setupNavController() {
        val navGraphIds = listOf(
            R.navigation.weather,
            R.navigation.settings
        )

        //
        //

        /**
         * Setup the bottom navigation view with a list of navigation graphs
         * We have two fragments @see Wea created for bottomNavigationView and One Fragment
         *
         * */
        currentNavController = bottomNavigationView?.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.navHostFragment,
            intent = intent
        )

    }

}
