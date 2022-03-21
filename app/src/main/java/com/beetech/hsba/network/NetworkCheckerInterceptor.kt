package com.beetech.hsba.network

import android.content.Context
import com.beetech.hsba.utils.DeviceUtil


import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

class NetworkCheckerInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (DeviceUtil.hasConnection(context)) {
            chain.proceed(chain.request())
        } else {
            throw NoConnectivityException()
        }
    }

    inner class NoConnectivityException : Exception() {
        override val message: String?
            get() = "I'm Died!"
    }
}
