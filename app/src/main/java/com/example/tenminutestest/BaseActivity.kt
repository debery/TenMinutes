package com.example.tenminutestest

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import android.os.Build
import android.view.View


open class BaseActivity:AppCompatActivity() {

    private val activities=ArrayList<Activity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("In which Activity", javaClass.simpleName)  //知晓当前是哪个activity


        // 设置状态栏和状态栏文字颜色(6.0)
        // 设置状态栏和状态栏文字颜色(6.0)
        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        activityAdd(this)//打开每一个activity的时候都执行让它加入activities的操作
    }

    private fun activityAdd(activity: Activity){
        activities.add(activity)
    }

    fun activityRemove(activity: Activity){
        activities.remove(activity)
    }

    fun activitiesFinish(){
        for(activity in activities){
            activity.finish()
            activities.remove(activity)
        }
    }
}