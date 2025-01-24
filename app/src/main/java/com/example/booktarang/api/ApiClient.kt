package com.example.booktarang.api

import com.example.booktarang.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient private constructor(){

    private val logInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .addInterceptor(logInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
//        .baseUrl("http://10.0.2.2:8000/api/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService: ApiService = retrofit.create(ApiService::class.java)

    companion object {
        private var instance: ApiClient? = null

        fun get(): ApiClient {
            if (instance == null) {
                instance = ApiClient()
            }

            return instance!!
        }
    }
}