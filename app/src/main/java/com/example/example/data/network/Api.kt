package com.example.example.data.network

import com.example.example.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("everything")
    suspend fun getNewsList(
        @Query("q") category: String,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String
    ): NewsResponse
}