package com.example.tenminutestest2

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.R.attr.statusBarColor

import android.os.Build
import android.view.View


open class BaseActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("In which Activity", javaClass.simpleName)
        // 设置状态栏和状态栏文字颜色(6.0)
        // 设置状态栏和状态栏文字颜色(6.0)
        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}