package com.example.tenminutestest2

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MyApplication:Application() {
    //获取全局context变量
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }
}