package com.example.tenminutestest.logic.touse

import android.util.Log
import com.example.tenminutestest.logic.model.GoodRequire
import com.example.tenminutestest.logic.model.UniveResponse
import com.example.tenminutestest.logic.network.GoodService
import com.example.tenminutestest.logic.network.ServiceCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoodUse (val id:String, val userid:String, tablename:String, target:String){

    private val goodRequire= GoodRequire(id,userid,tablename,target)

    fun add(){

        val goodService= ServiceCreator.create(GoodService::class.java)
        goodService.addGood(goodRequire).enqueue(object : Callback<UniveResponse> {
            override fun onResponse(
                call: Call<UniveResponse>,
                response: Response<UniveResponse>
            ) {
                Log.d("addGood",response.message())
                Log.d("addGood",response.isSuccessful.toString())
            }

            override fun onFailure(call: Call<UniveResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
    fun delete(){
        val goodService= ServiceCreator.create(GoodService::class.java)
        goodService.deleteGood(goodRequire).enqueue(object : Callback<UniveResponse> {
            override fun onResponse(
                call: Call<UniveResponse>,
                response: Response<UniveResponse>
            ) {
                Log.d("addGood",response.message())
            }

            override fun onFailure(call: Call<UniveResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}