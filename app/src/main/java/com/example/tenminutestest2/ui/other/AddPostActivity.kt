package com.example.tenminutestest2.ui.other

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import com.example.tenminutestest2.R
import com.example.tenminutestest2.logic.model.PostFromServer
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import kotlin.concurrent.thread

class AddPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        supportActionBar?.hide()
        val btnFinish:ImageView=findViewById(R.id.btnAddPostActivityFinish)
        val choosePlace:LinearLayout=findViewById(R.id.addPostActivityPlaceLayout)
        val picture1:ImageView=findViewById(R.id.picture1)
        val deliver:Button=findViewById(R.id.deliver)
        btnFinish.setOnClickListener {
            finish()
        }
        picture1.setOnClickListener {
            showPicturePopWindow()
            Toast.makeText(this,"暂不支持传输图片,此操作为无效操作",Toast.LENGTH_SHORT).show()
        }
        choosePlace.setOnClickListener {
            showPlacePopWindow()
            Toast.makeText(this,"目前暂且仅支持默认发表在艺术板块",Toast.LENGTH_SHORT).show()
        }
        deliver.setOnClickListener {
            postArtsAdd()//假设一定成功
            finish()
        }
    }

    private fun showPlacePopWindow(){
        val contentView= LayoutInflater.from(this).inflate(R.layout.add_post_place_pop,null)
        val popupWindow= PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.isFocusable=true
        popupWindow.setBackgroundDrawable(ColorDrawable(0x800000))
        popupWindow.isOutsideTouchable=true
        popupWindow.isTouchable=true

        popupWindow.showAtLocation(contentView,Gravity.CENTER,0,0)
        backgroundAlpha(0.5f)
        popupWindow.setOnDismissListener {
            popupWindow.dismiss()
            backgroundAlpha(1f)
        }

        //板块选择
        val btnTeach:Button=contentView.findViewById(R.id.btnTeach)
        val btnDraw:Button=contentView.findViewById(R.id.btnDraw)
        val btnSport:Button=contentView.findViewById(R.id.btnSport)
        val btnCancel:Button=contentView.findViewById(R.id.btnCancel)

        btnCancel.setOnClickListener {
            popupWindow.dismiss()
            backgroundAlpha(1f)
        }

    }

    //弹窗的控制代码
    private fun showPicturePopWindow(){

        val contentView= LayoutInflater.from(this).inflate(R.layout.add_post_picture_pop,null)
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
        val view: View =popupWindow.contentView
        val btnCamera:Button=view.findViewById(R.id.btnCamera)
        val btnAlbum:Button=view.findViewById(R.id.btnAlbum)
        val btnCancel:Button=view.findViewById(R.id.btnCancel)

        btnCancel.setOnClickListener {
            popupWindow.dismiss()
            backgroundAlpha(1f)
        }
        btnAlbum.setOnClickListener {
            val intent=Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type="image/*"
            startActivityForResult(intent,1)
        }

    }
    //设置透明度的代码
    private fun backgroundAlpha(alphaVal:Float){
        val alp: WindowManager.LayoutParams = this.window.attributes
        alp.alpha=alphaVal
        this.window.attributes=alp
        this.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val picture1:ImageView=findViewById(R.id.picture1)
        val picture2:ImageView=findViewById(R.id.picture2)
        when(requestCode){
            1->{
                if(resultCode==Activity.RESULT_OK && data!=null){
                    data.data?.let {
                        val bitmap=contentResolver.openFileDescriptor(it,"r")?.use {
                            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
                        }
                        picture1.setImageBitmap(bitmap)
                        picture2.visibility=View.VISIBLE
                    }
                }
            }
        }
    }

    private fun postArtsAdd(){
        val printTitle:EditText=findViewById(R.id.printTitle)
        val printContent:EditText=findViewById(R.id.printContent)
        thread {
            try{
                val requestBody= FormBody.Builder()
                    .add("nickname","测试员1号")
                    .add("post_title",printTitle.text.toString())
                    .add("content",printContent.text.toString())
                    .build()
                val client= OkHttpClient()
                val request= Request.Builder()
                    .url("http://120.24.191.82:8080/PostOfArts/add")
                    .post(requestBody)
                    .build()
                val response= client.newCall(request).execute()
                val responseData=response.body?.toString()
                if(responseData!=null){
                parseJSON(responseData)
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

    }
    private fun parseJSON(jsonData:String){
        val gson=Gson()
        val data=gson.fromJson(jsonData, PostFromServer::class.java)
        Log.d("AddPostActivity",data.message)
    }


}