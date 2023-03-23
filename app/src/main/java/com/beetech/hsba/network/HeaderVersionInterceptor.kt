package com.beetech.hsba.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderVersionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val headers = request.headers.newBuilder().add("version", "2.0.0").build()
        request = request.newBuilder().headers(headers).build()
        return chain.proceed(request)
    }
}