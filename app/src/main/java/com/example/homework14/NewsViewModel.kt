package com.example.homework14

import androidx.lifecycle.*
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    private var _newsLiveData = MutableLiveData<ResponseHandler<List<NewsData>>>()
    val newsDataLiveData: LiveData<ResponseHandler<List<NewsData>>> = _newsLiveData

    private var _newGeneratedNews = MutableLiveData(1)

    val newGeneratedNews = _newGeneratedNews.switchMap {
        RetrofitService.getNewPageResult().cachedIn(viewModelScope)
    }

    fun init() {
        CoroutineScope(ioDispatcher).launch {
            getNewsResponse()
        }
    }

    private suspend fun getNewsResponse() {
        val result = RetrofitService.getAllNews().getNews()

        _newsLiveData.postValue(ResponseHandler.Loading(true))
        if (result.isSuccessful) {
            _newsLiveData.postValue(ResponseHandler.Success(result.body()))
            _newsLiveData.postValue(ResponseHandler.Loading(false))
        } else {
            result.code()
            ResponseHandler.Loading(false)
            ResponseHandler.Error("${result.errorBody()}")
        }
    }
}