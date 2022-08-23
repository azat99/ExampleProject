package com.example.example.data.repository

import com.example.example.data.model.NewsResponse
import com.example.example.data.model.toResult
import com.example.example.data.network.Api
import com.example.example.domain.repository.Repository
import com.example.example.util.Result
import com.example.example.util.apiCall
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject

private const val NEWS_CATEGORY = "bitcoin"
private const val NEWS_PAGE_SIZE = 50

class RepositoryImpl @Inject constructor(
    retrofit: Retrofit
) : Repository {

    private val api = retrofit.create<Api>()

    private val apiKey: String = "e65ee0938a2a43ebb15923b48faed18d"

    override suspend fun getNews(): Result<NewsResponse> =
        apiCall { api.getNewsList(NEWS_CATEGORY, NEWS_PAGE_SIZE, apiKey).toResult { this } }

    private suspend inline fun <T> apiCall(request: () -> Result<T>) =
        apiCall(Dispatchers.IO, request)
}