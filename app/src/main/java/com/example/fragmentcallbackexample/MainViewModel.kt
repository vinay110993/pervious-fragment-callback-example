package com.example.fragmentcallbackexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){
    private val _status: MutableLiveData<String> = MutableLiveData()

    fun observerOnString(): LiveData<String> = _status

    fun updateString(value: String){
        _status.postValue(value)
    }

}