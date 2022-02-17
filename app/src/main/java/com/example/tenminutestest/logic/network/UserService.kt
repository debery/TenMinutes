package com.example.tenminutestest.logic.network

import com.example.tenminutestest.logic.model.ListPostResponse
import com.example.tenminutestest.logic.model.User
import com.example.tenminutestest.logic.model.UserData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("updateUserAvatar")
    fun updateAvatar(@Body user:UserData): Call<ListPostResponse>

    @POST("user/user")
    fun ouheming(@Body user: User):Call<ListPostResponse>
}