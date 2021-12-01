package com.example.tenminutestest.ui.main

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import androidx.fragment.app.FragmentActivity
import com.example.tenminutestest.MyApplication
import com.example.tenminutestest.R
import com.example.tenminutestest.ui.main.fragment.TeachTabFragment
import com.example.tenminutestest.ui.other.AddPostActivity


//用于在主界面的三个需要用到跳出同样的弹窗的代码，需要传入对应的ActivityFragment来进行操作

class ShowButton(private val frag:Int) {
    fun showAddWindow(activity: FragmentActivity?){
        val contentView= LayoutInflater.from(activity).inflate(R.layout.pop_create_post,null)
        val popupWindow= PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.isFocusable=true
        popupWindow.setBackgroundDrawable(ColorDrawable(0x800000))
        popupWindow.isOutsideTouchable=true
        popupWindow.isTouchable=true

        popupWindow.showAtLocation(contentView, Gravity.BOTTOM,0,0)
        //弹出窗口后将背景虚化
        backgroundAlpha(0.5f,activity)
        popupWindow.setOnDismissListener {
            popupWindow.dismiss()
            backgroundAlpha(1f,activity)
        }

        //获取popupWindow里的控件之前，使用view保存R.layout.pop生成的contentView
        val view: View =popupWindow.contentView
        val btnAdd: Button =view.findViewById(R.id.btnAdd)
        val btnCancel: Button =view.findViewById(R.id.btnCancelInPop)

        //点击”动态“
        btnAdd.setOnClickListener {
            popupWindow.dismiss()
            backgroundAlpha(1f,activity)
            val intent= Intent(activity, AddPostActivity::class.java)
            intent.putExtra("fragment",frag)
            activity?.startActivityForResult(intent,1)
        }

        btnCancel.setOnClickListener {
            popupWindow.dismiss()
            backgroundAlpha(1f,activity)
        }
    }


    //设置透明度的代码
    //本体函数在MainActivity
    private fun backgroundAlpha(alphaVal:Float,activity: FragmentActivity?){
        val act:MainActivity=activity as MainActivity
        act.backgroundAlpha(alphaVal)
    }

}