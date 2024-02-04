package com.rowen.alocation.repository

import androidx.lifecycle.MutableLiveData

object UserRepository {

    var mLoginStatusLiveData = MutableLiveData<Int>()

    fun getLoginStatus(): MutableLiveData<Int> {
        return mLoginStatusLiveData
    }

    init {
        mLoginStatusLiveData.postValue(0)
    }
}