package com.example.sliit_news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FragmentViewModel: ViewModel() {

    // Private variable to hold the background color
    private val backgroundColor = MutableLiveData<Int>()

    // Function to get the background color as LiveData
    fun getBackgroundColor(): LiveData<Int> {
        return backgroundColor
    }
    // Function to set the background color
    fun setBackground(color:Int){
        backgroundColor.value = color
    }
}