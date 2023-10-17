package com.example.sliit_news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sliit_news.database.News

class MainActivityData:ViewModel() {

    // MutableLiveData to hold a list of News objects
    private val _data = MutableLiveData<List<News>>()

    // LiveData reference for observing the list of News objects
    val data:LiveData<List<News>> = _data

    // Function to set the list of News objects
    fun setData(data:List<News>){
        _data.value = data
    }
}