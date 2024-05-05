package com.example.retrofit.api

import com.example.retrofit.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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

    @PUT("/posts/{id}")
    suspend fun putPost(
        @Path("id")id:Int,
        @Body user: User
    ):Response<User>

    @PATCH("/posts/{id}")
    suspend fun patchPost(
        @Path("id")id:Int,
        @Body user: User
    ):Response<User>

    @DELETE("/posts/{id}")
    suspend fun deletePost(
        @Path("id")id:Int
    ):Response<Unit>

    //in delete operation we get the response as unit because delete return only the
    //status code of operation
    //we can also return the Response<User> but unit is more significant


}