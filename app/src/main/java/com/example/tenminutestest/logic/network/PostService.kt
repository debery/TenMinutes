package com.example.tenminutestest.logic.network

import com.example.tenminutestest.logic.model.*
import retrofit2.Call


import retrofit2.http.Body
import retrofit2.http.POST

interface PostService {

    //add
    @POST("post/postapost")
    fun postPost(@Body post: AddPostRequire): Call<UniveResponse>

    //get
    @POST("post/getapost")
    fun getPost(@Body message:GetPostRequire):Call<GetPostResponse>

    //list
    @POST("post/getposts")
    fun getPosts(@Body listPostRequire: ListPostRequire):Call<ListPostResponse>

    //delete
    @POST("PostOfTeaching/delete")
    fun deletePostOfTeaching(@Body post: PostB):Call<ListPostResponse>
    @POST("PostOfArts/delete")
    fun deletePostOfArts(@Body post: PostB):Call<ListPostResponse>
    @POST("PostOfSports/delete")
    fun deletePostOfSport(@Body post: PostB):Call<ListPostResponse>


}