package com.rowen.alocation.connect

import com.rowen.alocation.connect.been.UserLoginResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @GET("example/get")
    fun getExample(): Call<UserLoginResponse>

    @POST("example/post")
    @FormUrlEncoded
    fun postExample(@Field("param1") param1: String, @Field("param2") param2: String): Call<UserLoginResponse>

//    @Multipart
//    @POST("example/upload")
//    fun uploadImage(
//        @Part("description") description: RequestBody,
//        @Part image: MultipartBody.Part
//    ): Call<ApiResponse>

}