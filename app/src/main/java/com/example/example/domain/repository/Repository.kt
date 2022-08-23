package com.example.example.domain.repository

import com.example.example.data.model.NewsResponse
import com.example.example.util.Result

interface Repository {
    suspend fun getNews(): Result<NewsResponse>
}