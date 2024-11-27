package com.example.booktarang.api

import com.example.booktarang.global.App
import com.example.booktarang.global.AppEncryptPref
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = AppEncryptPref.get().getToken(App.get())
        if (token == null) {
            val request = chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .build()
            return chain.proceed(request)
        } else {
            val request = chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization","Bearer $token")
                .build()
            return chain.proceed(request)
        }
    }
}