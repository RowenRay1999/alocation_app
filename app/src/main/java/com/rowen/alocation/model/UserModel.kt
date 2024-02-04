package com.rowen.alocation.model

import com.rowen.alocation.connect.ApiClient
import com.rowen.alocation.model.callback.UserLoginCallback

object UserModel {

//    val UserLoginCallbackList = ArrayList<UserLoginCallback>()

    fun sendGetExample(callback: UserLoginCallback) {
        // GET请求示例
        val getCall = ApiClient.apiService.getExample()
        val getResponse = getCall.execute()
        if (getResponse.isSuccessful) {
            val result = getResponse.body()
            // 处理结果
            callback.callback()
        } else {
            // 处理错误
        }
    }

    fun sendPostExample(callback: UserLoginCallback) {
        // POST请求示例
        val postCall = ApiClient.apiService.postExample("value1", "value2")
        val postResponse = postCall.execute()
        if (postResponse.isSuccessful) {
            val result = postResponse.body()
            // 处理结果
            callback.callback()
        } else {
            // 处理错误
        }
    }

}