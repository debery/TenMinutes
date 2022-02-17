package com.example.tenminutestest.logic.model

import android.util.Log
import com.example.tenminutestest.logic.network.GoodService
import com.example.tenminutestest.logic.network.ServiceCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UniveResponse(val success:Boolean, val message:String, val code:String)

class GoodRequire(val id:String, val userid:String, val tablename:String, val target:String)


