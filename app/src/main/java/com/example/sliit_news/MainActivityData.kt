package com.example.sliit_news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sliit_news.database.News

class MainActivityData:ViewModel() {


    private val _data = MutableLiveData<List<News>>()

    val data:LiveData<List<News>> = _data

    fun setData(data:List<News>){
        _data.value = data
    }
}