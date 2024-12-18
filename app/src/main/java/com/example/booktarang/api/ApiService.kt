package com.example.booktarang.api

import com.example.booktarang.model.ApiResponse
import com.example.booktarang.model.Data
import com.example.booktarang.model.LoginResponse
import com.example.booktarang.model.RegisterResponse
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

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirmation: String
    ): ApiResponse<RegisterResponse>

    @GET("user")
    suspend fun loadProfile(): ApiResponse<User>

    @GET("fields")
    suspend fun loadFields(): ApiResponse<List<RealField>>

    @GET("sport-type")
    suspend fun loadDataDisplay(): ApiResponse<List<Data>>
}