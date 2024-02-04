package com.rowen.alocation.vm

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rowen.alocation.repository.UserRepository
import com.rowen.alocation.repository.been.PosiInfoBeen


class MainViewModel(lifecycle: LifecycleOwner) : ViewModel() {
    private val mLoginStatusLiveData: MutableLiveData<Int> = MutableLiveData<Int>()
    private val mPosiInfoLiveData: MutableLiveData<PosiInfoBeen> = MutableLiveData<PosiInfoBeen>()

    init {
        UserRepository.getLoginStatus().observe(lifecycle) {
            mLoginStatusLiveData.postValue(it)
        }
    }

    fun getLoginStatus(): MutableLiveData<Int> {
        return mLoginStatusLiveData
    }

    fun getPosiInfo(): MutableLiveData<PosiInfoBeen> {
        return mPosiInfoLiveData
    }
}