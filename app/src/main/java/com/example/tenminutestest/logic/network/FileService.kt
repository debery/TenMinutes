package com.example.tenminutestest.logic.network

import com.example.tenminutestest.logic.model.FileResponse
import com.example.tenminutestest.logic.model.PostResponse
import com.example.tenminutestest.logic.model.PostUp
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface FileService {

    @Multipart
    @POST("uploadFile")
    fun uploadFile( @Part file:MultipartBody.Part): Call<FileResponse>

    @Multipart
    @POST("uploadMultipleFiles")
    fun uploadFiles(@PartMap map:Map<String,RequestBody>): Call<List<FileResponse>>

}