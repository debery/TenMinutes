package com.example.tenminutestest.logic.network

import com.example.tenminutestest.logic.model.GoodRequire
import com.example.tenminutestest.logic.model.UniveResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GoodService {

    @POST("goods/addgoods")
    fun addGood(@Body goodRequire: GoodRequire):Call<UniveResponse>

    @POST("goods/deletegoods")
    fun deleteGood(@Body goodRequire: GoodRequire):Call<UniveResponse>
}