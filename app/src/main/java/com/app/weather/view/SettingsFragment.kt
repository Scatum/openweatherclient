package com.app.weather.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.Observer
import com.app.weather.R
import com.app.weather.activity.MainActivity
import com.app.weather.constant.AppTheme
import com.app.weather.service.WorkerService
import com.app.weather.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment() {
    private val workerService: WorkerService by inject()
    private val settingsViewModel: SettingsViewModel by viewModel()

    override fun getLayoutId() = R.layout.fragment_settings
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDarkModeSwitcher()
        setupUpdaterTimer()
    }

    private fun setupUpdaterTimer() {
        settingsViewModel.getUpdaterOption().observe(viewLifecycleOwner, Observer {
            timerChoiceRadioGroup?.findViewWithTag<RadioButton>(it.toString())?.isSelected = true
            timerChoiceRadioGroup?.findViewWithTag<RadioButton>(it.toString())?.isChecked = true
        })


        timerChoiceRadioGroup?.setOnCheckedChangeListener { group, checkedId ->
            val selectedOption =
                group?.findViewById<RadioButton>(checkedId)?.tag?.toString()?.toLong()
            if (selectedOption != null) {
                settingsViewModel.saveUpdaterOption(selectedOption).observe(viewLifecycleOwner,
                    Observer {
                        if (selectedOption > 0) {
                            workerService.setup()
                        }else{
                            workerService.cancel()
                        }
                    })
            }

        }
    }

    private fun setupDarkModeSwitcher() {
        settingsViewModel.getInterfaceMode().observe(viewLifecycleOwner, Observer {
            switchDarkMode.isChecked = it != AppTheme.LIGHT
            switchDarkMode?.setOnCheckedChangeListener { _, isChecked ->
                settingsViewModel.setInterfaceMode(
                    if (isChecked) AppTheme.DARK
                    else AppTheme.LIGHT
                ).observe(viewLifecycleOwner, Observer {
                    restartApp()
                })
            }
        })


    }

    private fun restartApp() {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
