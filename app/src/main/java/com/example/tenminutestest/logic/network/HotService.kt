package com.example.tenminutestest.logic.network

import com.example.tenminutestest.logic.model.HotWord
import retrofit2.Call
import retrofit2.http.POST

interface HotService {

    @POST("hotpage/update")
    fun updateHotWord()

    @POST("hotpage/time")
    fun getHotWord():Call<List<HotWord>>
}