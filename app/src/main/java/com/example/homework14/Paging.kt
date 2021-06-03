package com.example.homework14

import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.lang.Exception

private const val FIRST_PAGE = 1

class Paging(val news: RetrofitService) : PagingSource<Int, NewsData>() {

    override fun getRefreshKey(state: PagingState<Int, NewsData>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsData> {
        return try {
            val page = params.key ?: FIRST_PAGE
            val response = news.getAllNews().getNews()

            LoadResult.Page(
                data = response.body() ?: emptyList(),
                prevKey = if (page == FIRST_PAGE) null else page,
                nextKey = if (response.body()!!.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}