package com.example.retrofit.api

import com.example.retrofit.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @GET("/posts/1")
    suspend fun getUser():Response<User>
    @GET("/posts")
    suspend fun getAllUser():Response<List<User>>
    @POST("/posts")
    suspend fun createPost(
        @Body user: User
    ):Response<User>

    //lets assume a server doesn't accept a json we need to send the string
    //so for this we send the form urls
    @FormUrlEncoded
    @POST("/posts")
    suspend fun createPostUrls(
        @Field("userId")userId:Int,
        @Field("title")title:String,
        @Field("body")body:String

    ):Response<User>
}