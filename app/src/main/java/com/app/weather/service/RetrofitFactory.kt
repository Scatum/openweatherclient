package com.app.weather.service

import com.app.weather.builder.AppBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitFactory: KoinComponent {


    fun makeRetrofitService(): AppRetrofitService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val apiClient =
            OkHttpClient.Builder().addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val request = chain.request().newBuilder()
                        .build()//can be added some headers here, for this app we don't have any header
                    chain.proceed(request)
                }).addInterceptor(logging).readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS).build()
        return Retrofit.Builder()
            .baseUrl(AppBuilder.getBaseUrl())
            .client(apiClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(AppRetrofitService::class.java)
    }

}