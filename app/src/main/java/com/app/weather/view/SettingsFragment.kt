package com.app.weather.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import com.app.weather.R
import com.app.weather.activity.MainActivity
import com.app.weather.constant.AppTheme
import com.app.weather.service.PreferenceService
import com.app.weather.service.WorkerService
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.ext.android.inject

class SettingsFragment : BaseFragment() {

    private val preferenceService: PreferenceService by inject()
    private val workerService: WorkerService by inject()

    override fun getLayoutId() = R.layout.fragment_settings
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDarkModeSwitcher()
        setupUpdaterTimer()
    }

    private fun setupUpdaterTimer() {
        val selected = preferenceService.getOptionType()
        timerChoiceRadioGroup?.findViewWithTag<RadioButton>(selected.toString())?.isSelected = true
        timerChoiceRadioGroup?.findViewWithTag<RadioButton>(selected.toString())?.isChecked = true

        timerChoiceRadioGroup?.setOnCheckedChangeListener { group, checkedId ->
            val selectedOption =
                group?.findViewById<RadioButton>(checkedId)?.tag?.toString()?.toLong()
            selectedOption?.let { preferenceService.saveOptionType(it) }
            workerService.setup()
        }
    }

    private fun setupDarkModeSwitcher() {
        switchDarkMode.isChecked = preferenceService.getInterfaceMode() != AppTheme.LIGHT
        switchDarkMode?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                preferenceService.saveInterfaceMode(AppTheme.DARK)
            } else {
                preferenceService.saveInterfaceMode(AppTheme.LIGHT)
            }
            restartApp()
        }
    }

    private fun restartApp() {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
