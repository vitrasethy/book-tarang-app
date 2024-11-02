package com.example.booktarang.api

import com.example.booktarang.model.ApiResponse
import com.example.booktarang.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("profile")
    suspend fun loadProfile(): ApiResponse<User>
}