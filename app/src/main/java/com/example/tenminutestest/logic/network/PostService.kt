package com.example.tenminutestest.logic.network

import com.example.tenminutestest.logic.model.PostB
import retrofit2.Call
import com.example.tenminutestest.logic.model.PostUp
import com.example.tenminutestest.logic.model.ResponseFromServer


import retrofit2.http.Body
import retrofit2.http.POST

interface PostService {

    //add
    @POST("PostOfTeaching/add")
    fun addPostOfTeaching(@Body post: PostUp): Call<ResponseFromServer>
    @POST("PostOfArts/add")
    fun addPostOfArts(@Body post: PostUp): Call<ResponseFromServer>
    @POST("PostOfSport/add")
    fun addPostOfSport(@Body post: PostUp): Call<ResponseFromServer>

    //list
    @POST("PostOfTeaching/list")
    fun listPostOfTeaching():Call<ResponseFromServer>
    @POST("PostOfArts/list")
    fun listPostOfArts():Call<ResponseFromServer>
    @POST("PostOfSport/list")
    fun listPostOfSport():Call<ResponseFromServer>

    //delete
    @POST("PostOfTeaching/delete")
    fun deletePostOfTeaching(@Body post: PostB):Call<ResponseFromServer>
    @POST("PostOfArts/delete")
    fun deletePostOfArts(@Body post: PostB):Call<ResponseFromServer>
    @POST("PostOfSport/delete")
    fun deletePostOfSport(@Body post: PostB):Call<ResponseFromServer>

}