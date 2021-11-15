package com.example.tenminutestest2.logic.network

import retrofit2.Call
import com.example.tenminutestest2.logic.model.PostB
import com.example.tenminutestest2.logic.model.ResponseFromServer
import okhttp3.ResponseBody


import retrofit2.http.Body
import retrofit2.http.POST

interface PostService {

    @POST("PostOfArts/add")
    fun addPostOfArts(@Body post:PostB): Call<ResponseFromServer>
}