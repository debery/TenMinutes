package com.example.tenminutestest.logic.network

import com.example.tenminutestest.logic.model.CommentResponse
import com.example.tenminutestest.logic.model.PostB
import com.example.tenminutestest.logic.model.PostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CommentService {

    //传入post_id
    @POST("CommentOfTeaching/commentlist")
    fun listCommentOfTeaching(@Body post: PostB): Call<CommentResponse>
    @POST("CommentOfArts/commentlist")
    fun listCommentOfArts(@Body post: PostB): Call<CommentResponse>
    @POST("CommentOfSport/commentlist")
    fun listCommentOfSport(@Body post: PostB): Call<CommentResponse>
}