package com.example.sliit_news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FragmentViewModel: ViewModel() {

    private val backgroundColor = MutableLiveData<Int>()


    fun getBackgroundColor(): LiveData<Int> {
        return backgroundColor
    }
    fun setBackground(color:Int){
        backgroundColor.value = color
    }
}