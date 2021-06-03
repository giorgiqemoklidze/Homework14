package com.example.homework14

import com.example.homework14.RetrofitService.END_POINT
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitRepository {

    @GET(END_POINT)
    suspend fun getNews(): Response<List<NewsData>>

}