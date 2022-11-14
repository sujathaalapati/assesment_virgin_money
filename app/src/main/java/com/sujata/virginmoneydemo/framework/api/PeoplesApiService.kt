package com.sujata.virginmoneydemo.framework.api

import com.sujata.virginmoneydemo.framework.api.dto.Peoples
import com.sujata.virginmoneydemo.framework.api.dto.Rooms
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface PeoplesApiService {
    @GET("people")
    suspend fun getPeoples(): Response<Peoples>

    @GET("rooms")
    suspend fun getRooms(): Response<Rooms>
}