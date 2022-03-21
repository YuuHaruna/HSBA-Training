package com.beetech.hsba.di.module

import android.content.Context
import com.beetech.hsba.BuildConfig
import com.beetech.hsba.network.ApiInterface
import com.beetech.hsba.network.NetworkCheckerInterceptor
import com.beetech.hsba.utils.Define

import com.google.gson.GsonBuilder

import java.util.concurrent.TimeUnit

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApiInterface(client: OkHttpClient): ApiInterface {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpClient(context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

//        val tokenInterceptor = TokenInterceptor()
        val networkCheckerInterceptor = NetworkCheckerInterceptor(context)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
//            .addInterceptor(tokenInterceptor)
            .addInterceptor(networkCheckerInterceptor)
            .connectTimeout(Define.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Define.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
}
