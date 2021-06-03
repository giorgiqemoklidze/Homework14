package com.example.homework14

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = "http://139.162.207.17/"
    const val END_POINT = "api/m/v2/news"

    fun getAllNews(): RetrofitRepository {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitRepository::class.java)
    }

    fun getNewPageResult() = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false,
        ),
        pagingSourceFactory = { Paging(RetrofitService) }
    ).liveData
}