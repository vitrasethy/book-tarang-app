package com.example.booktarang.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {
    private var dataService: SportTypeService? = null

    fun getDataService(): SportTypeService{
        if(dataService == null){
            val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            dataService = retrofit.create(SportTypeService::class.java)
        }

        return dataService!!
    }
}