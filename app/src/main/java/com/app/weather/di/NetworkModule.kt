package com.app.weather.di

import com.app.weather.data.source.network.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import com.app.weather.BuildConfig
import com.app.weather.core.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setStrictness(Strictness.LENIENT).create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        return if (BuildConfig.DEBUG) {
            val loginInterceptor = HttpLoggingInterceptor()
            loginInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
            loginInterceptor.level = HttpLoggingInterceptor.Level.BODY

            httpClient.addInterceptor(loginInterceptor)
                .readTimeout(Constants.NETWORK_TIME_OUT, TimeUnit.SECONDS)
                .build()
        } else {
            httpClient
                .readTimeout(Constants.NETWORK_TIME_OUT, TimeUnit.SECONDS)
                .build()
        }

    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiService::class.java)
    }
}