package com.app.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.app.weather.constant.AppTheme
import com.app.weather.repository.LocalDataRepository
import kotlinx.coroutines.Dispatchers.IO

class SettingsViewModel(
    private val localDataRepository: LocalDataRepository
) : ViewModel() {

    fun getUpdaterOption(): LiveData<Long> = liveData(IO) {
        emit(localDataRepository.getUpdaterOption())
    }


    fun saveUpdaterOption(option: Long): LiveData<Boolean> = liveData(IO) {
        var success = false
        localDataRepository.setUpdaterOption(option)?.let {
            it?.let { success = it }
            emit(success)
        }
    }


    fun getInterfaceMode(): LiveData<AppTheme> = liveData(IO) {
        emit(localDataRepository.getInterfaceMode())
    }


    fun setInterfaceMode(appTheme: AppTheme): LiveData<Boolean> = liveData(IO) {
        emit(localDataRepository.setInterfaceMode(appTheme))
    }

    override fun onCleared() {
        super.onCleared()
    }


}