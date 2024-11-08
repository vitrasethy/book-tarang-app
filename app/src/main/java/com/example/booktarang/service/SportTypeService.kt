package com.example.booktarang.service

import com.example.booktarang.model.ApiResponse
import com.example.booktarang.model.Data
import retrofit2.http.GET

interface SportTypeService {
    @GET("/api/sport-type")
    suspend fun loadDataDisplay(): ApiResponse<List<Data>>

}