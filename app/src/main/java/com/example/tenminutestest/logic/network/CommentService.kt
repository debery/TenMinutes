package com.example.tenminutestest.logic.network

import com.example.tenminutestest.logic.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CommentService {


    @POST("comment/getcommentbypost")
    fun getComments(@Body getCommentsRequire: GetCommentsRequire): Call<CommentResponse>

    //add
    @POST("comment/addcomment")
    fun addComment(@Body addCommentRequire: AddCommentRequire):Call<AddCommentResponse>
}