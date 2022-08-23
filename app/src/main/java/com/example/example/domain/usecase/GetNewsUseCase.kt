package com.example.example.domain.usecase

import com.example.example.data.model.NewsResponse
import com.example.example.domain.repository.Repository
import com.example.example.util.Result
import com.example.example.util.toFailure
import javax.inject.Inject

interface GetNewsUseCase {

    suspend fun execute(): Result<NewsResponse>

    class Default @Inject constructor(
        private val repository: Repository
    ) : GetNewsUseCase {

        override suspend fun execute(): Result<NewsResponse> = try {
            repository.getNews()
        } catch (t: Throwable) {
            t.message.orEmpty().toFailure()
        }
    }
}