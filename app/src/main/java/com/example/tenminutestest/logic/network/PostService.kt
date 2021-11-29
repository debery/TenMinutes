package com.example.tenminutestest.logic.network

import com.example.tenminutestest.logic.model.PostB
import retrofit2.Call
import com.example.tenminutestest.logic.model.PostUp
import com.example.tenminutestest.logic.model.PostResponse


import retrofit2.http.Body
import retrofit2.http.POST

interface PostService {

    //add
    @POST("PostOfTeaching/add")
    fun addPostOfTeaching(@Body post: PostUp): Call<PostResponse>
    @POST("PostOfArts/add")
    fun addPostOfArts(@Body post: PostUp): Call<PostResponse>
    @POST("PostOfSports/add")
    fun addPostOfSport(@Body post: PostUp): Call<PostResponse>

    //list
    @POST("PostOfTeaching/list")
    fun listPostOfTeaching():Call<PostResponse>
    @POST("PostOfArts/list")
    fun listPostOfArts():Call<PostResponse>
    @POST("PostOfSports/list")
    fun listPostOfSport():Call<PostResponse>

    //delete
    @POST("PostOfTeaching/delete")
    fun deletePostOfTeaching(@Body post: PostB):Call<PostResponse>
    @POST("PostOfArts/delete")
    fun deletePostOfArts(@Body post: PostB):Call<PostResponse>
    @POST("PostOfSports/delete")
    fun deletePostOfSport(@Body post: PostB):Call<PostResponse>

}