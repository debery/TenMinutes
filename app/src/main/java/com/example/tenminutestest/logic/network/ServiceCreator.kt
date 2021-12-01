package com.example.tenminutestest.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
//  旧接口号  120.24.191.82
    private const val BASE_URL="http://114.55.7.232:8080/"
    private const val BASE_URL_OHM="http://10.12.159.19:8080/"
    private const val FILE_URL="http://114.55.7.232:8081/"//文件接口在另一个端口号
    private val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val retrofitOfFile=Retrofit.Builder()
        .baseUrl(FILE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val ouheming=Retrofit.Builder()
        .baseUrl(FILE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass:Class<T>):T= retrofit.create(serviceClass)
    fun <T> create2(serviceClass:Class<T>):T= retrofitOfFile.create(serviceClass)
    fun <T> createOHM(serviceClass: Class<T>):T= ouheming.create(serviceClass)
}