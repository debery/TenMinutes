package com.example.tenminutestest.ui.main

import android.content.Intent
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.MediaController
import android.widget.PopupWindow
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.example.tenminutestest.R
import com.example.tenminutestest.ui.other.AddPostActivity
import com.example.tenminutestest.ui.other.NoticeActivity
import com.example.tenminutestest.ui.other.SearchActivity

class SportTabFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sport, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val btnSearch: Button? =view?.findViewById(R.id.btnSearch)
        val btnAddFolder: Button? =view?.findViewById(R.id.addFolder)
        val btnNotice: Button? =view?.findViewById(R.id.notice)

        btnAddFolder?.setOnClickListener {
            showPopWindow()
        }
        btnNotice?.setOnClickListener {
            val intent = Intent(activity, NoticeActivity::class.java)
            startActivity(intent)
        }
        btnSearch?.setOnClickListener {
            val intent=Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }

        val videoView: VideoView? = view?.findViewById(R.id.videoView)
        activity?.window?.setFormat(PixelFormat.TRANSLUCENT)
        val url = Uri.parse("android.resource://${activity?.packageName}/${R.raw.video}")
        videoView?.setMediaController(MediaController(activity))
        videoView?.setVideoURI(url)
    }

    override fun onDestroy() {
        super.onDestroy()
        val videoView: VideoView? = view?.findViewById(R.id.videoView)
        videoView?.suspend()
    }

    private fun showPopWindow(){

        val contentView= LayoutInflater.from(activity).inflate(R.layout.pop_create_post,null)
        val popupWindow= PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.isFocusable=true
        popupWindow.setBackgroundDrawable(ColorDrawable(0x800000))
        popupWindow.isOutsideTouchable=true
        popupWindow.isTouchable=true

        popupWindow.showAtLocation(contentView, Gravity.BOTTOM,0,0)
        //弹出窗口后将背景虚化
        backgroundAlpha(0.5f)
        popupWindow.setOnDismissListener {
            popupWindow.dismiss()
            backgroundAlpha(1f)
        }

        //获取popupWindow里的控件之前，使用view保存R.layout.pop生成的contentView
        val view:View =popupWindow.contentView
        val btnAdd:Button=view.findViewById(R.id.btnAdd)
        val btnCancel:Button=view.findViewById(R.id.btnCancelInPop)



        //点击”动态“
        btnAdd.setOnClickListener {
            popupWindow.dismiss()
            backgroundAlpha(1f)
            val intent= Intent(activity, AddPostActivity::class.java)
            startActivity(intent)
        }

        btnCancel.setOnClickListener {
            popupWindow.dismiss()
            backgroundAlpha(1f)
        }
    }

    //设置透明度的代码
    private fun backgroundAlpha(alphaVal:Float){
        val activity:MainActivity= activity as MainActivity
        activity.backgroundAlpha(alphaVal)
    }

}