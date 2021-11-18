package com.example.tenminutestest2.logic.network

import retrofit2.Call
import com.example.tenminutestest2.logic.model.PostUp
import com.example.tenminutestest2.logic.model.ResponseFromServer


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
    fun deletePostOfTeaching(@Body id:Int):Call<ResponseFromServer>
    @POST("PostOfArts/delete")
    fun deletePostOfArts(@Body id:Int):Call<ResponseFromServer>
    @POST("PostOfSport/delete")
    fun deletePostOfSport(@Body id:Int):Call<ResponseFromServer>

}