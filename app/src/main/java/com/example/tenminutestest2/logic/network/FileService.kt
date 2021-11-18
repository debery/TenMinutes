package com.example.tenminutestest2.logic.network

import com.example.tenminutestest2.logic.model.FileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import java.io.File

interface FileService {

    @Multipart
    @POST("uploadFile")
    fun uploadFile( @Part file:MultipartBody.Part): Call<FileResponse>

    @Multipart
    @POST("uploadMultipleFiles")
    fun uploadFiles(@PartMap map:Map<String,RequestBody>): Call<List<FileResponse>>

}