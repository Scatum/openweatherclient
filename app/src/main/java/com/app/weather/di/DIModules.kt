package com.app.weather.di

import com.app.weather.db.AppDatabase
import com.app.weather.repository.LocalDataRepository
import com.app.weather.repository.NetworkDataRepository
import com.app.weather.service.*
import com.app.weather.viewmodel.WeatherViewModel
import org.koin.core.module.Module
import org.koin.dsl.module


val appRepositories: Module = module {
    single { NetworkDataRepository(get(), get()) }
    single { LocalDataRepository(get(), get(), get()) }
}


val appViewModels: Module = module {
    single { WeatherViewModel(get(), get(), get()) }
}


val appServices: Module = module {
    single { AppRetrofitService.create() }
    single { AppSettingsService() }
    single { AppDatabase.getDatabase(get()) }
    single { NotificationService(get()) }
    single { NetworkService(get()) }
    single { WorkerService() }
    single { PreferenceService(get()) }
}