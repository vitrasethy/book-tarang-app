package com.example.booktarang.api

import com.example.booktarang.model.ApiResponse
import com.example.booktarang.model.BookingData
import com.example.booktarang.model.BookingResponse
import com.example.booktarang.model.Data
import com.example.booktarang.model.LoginResponse
import com.example.booktarang.model.RegisterResponse
import com.example.booktarang.model.User
import com.example.booktarang.model.Field as RealField
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("fields/{field}")
    suspend fun loadField(@Path("field") fieldId: Int): ApiResponse<RealField>

    @GET("fields")
    suspend fun loadFields(): ApiResponse<List<RealField>>

    @GET("sport-types/{type}/fields")
    suspend fun loadFieldsBySportType(@Path("type") sportTypeId: Int): ApiResponse<List<RealField>>

    @GET("sport-type")
    suspend fun loadDataDisplay(): ApiResponse<List<Data>>

    @GET("bookings")
    suspend fun loadBookings(): ApiResponse<List<BookingData>>

    @FormUrlEncoded
    @POST("bookings")
    suspend fun bookings(@Field("field_id") fieldId: Int,@Field("end_date") endDate: String, @Field("start_date") startDate: String): ApiResponse<BookingResponse>

}