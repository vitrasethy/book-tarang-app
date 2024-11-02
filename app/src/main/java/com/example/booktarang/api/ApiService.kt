package com.example.booktarang.api

import com.example.booktarang.model.ApiResponse
import com.example.booktarang.model.Field
import com.example.booktarang.model.User
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("profile")
    suspend fun loadProfile(): ApiResponse<User>

    @GET("/fields")
    suspend fun getFields(): ApiResponse<Field>
}