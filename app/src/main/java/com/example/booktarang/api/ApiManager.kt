package com.example.booktarang.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {
    private var apiService: ApiService? = null

    fun getApiService(): ApiService {
        if(apiService == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://localhost:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            apiService = retrofit.create(ApiService::class.java)
        }

        return apiService!!
    }
}