package com.example.tenminutestest.logic.network

import com.example.tenminutestest.logic.model.CommentResponse
import com.example.tenminutestest.logic.model.CommentUp
import com.example.tenminutestest.logic.model.PostB
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CommentService {

    //传入post_id,方便起见，直接传入post
    @POST("commentOfTeaching/commentlist")
    fun listCommentOfTeaching(@Body post: PostB): Call<CommentResponse>
    @POST("commentOfArts/commentlist")
    fun listCommentOfArts(@Body post: PostB): Call<CommentResponse>
    @POST("commentOfSport/commentlist")
    fun listCommentOfSport(@Body post: PostB): Call<CommentResponse>

    //add
    @POST("commentOfTeaching/add")
    fun addCommentOfTeaching(@Body commentUp: CommentUp):Call<CommentResponse>
    @POST("commentOfArts/add")
    fun addCommentOfArts(@Body commentUp: CommentUp):Call<CommentResponse>
    @POST("commentOfSport/add")
    fun addCommentOfSport(@Body commentUp: CommentUp):Call<CommentResponse>
}