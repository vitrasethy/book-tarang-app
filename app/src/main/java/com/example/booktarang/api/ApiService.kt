package com.example.booktarang.api

import com.example.booktarang.model.ApiResponse
import com.example.booktarang.model.LoginResponse
import com.example.booktarang.model.User
import com.example.booktarang.model.Field as RealField
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(@Field("email") email: String, @Field("password") password: String): ApiResponse<LoginResponse>

    @GET("user")
    suspend fun loadProfile(): ApiResponse<User>

    @GET("fields/1")
    suspend fun loadField(): ApiResponse<RealField>
}