package com.example.tenminutestest2.ui.main


import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tenminutestest2.R
import com.example.tenminutestest2.logic.model.PostB
import com.example.tenminutestest2.logic.model.ResponseFromServer
import com.example.tenminutestest2.logic.network.PostService
import com.example.tenminutestest2.logic.network.ServiceCreator
import com.example.tenminutestest2.ui.main.adapter.GeneralPostAdapter
import com.example.tenminutestest2.ui.other.AddPostActivity
import com.example.tenminutestest2.ui.other.NoticeActivity
import com.example.tenminutestest2.ui.other.SearchActivity
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.concurrent.thread


class DrawTabFragment: Fragment() {

    /*
    * 暂且无法得知这些代码是否能够使用，似乎post没有成功
    * */
    private val postBList=ArrayList<PostB>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_art,container,false)
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

        postArtsList()
        val recycler: RecyclerView? = view?.findViewById(R.id.recycler)
        recycler?.layoutManager = LinearLayoutManager(activity)
        val adapter = GeneralPostAdapter(postBList)
        recycler?.adapter=adapter

        val btn: Button? = view?.findViewById(R.id.button)
        btn?.setOnClickListener {
            postArtsList()
            recycler?.adapter=adapter
        }
    }


    //使用okhttp调用接口
    /*private fun postArtsList(){
        thread {
            try{
                val requestBody=FormBody.Builder().build()
                val client=OkHttpClient()
                val request=Request.Builder()
                    .url("http://120.24.191.82:8080/PostOfArts/list")
                    .post(requestBody)
                    .build()
                val response=client.newCall(request).execute()
                val responseData=response.body?.string()
                if(responseData!=null){
                    parseJSON(responseData)
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    //解析接口返回的JSON数据
    private fun parseJSON(jsonData:String){
        try {
            val gson= Gson()
            val postFromServer =gson.fromJson(jsonData, ResponseFromServer::class.java)
            //val list=gson.fromJson<List<PostB>>(postFromServer.items.toString(),object :TypeToken<List<PostB>>(){}.type)
            postBList.addAll(postFromServer.items)
            Log.d("wwwwwww","${postFromServer.success}")
            Log.d("wwwwwww","${postFromServer.items}")

        }catch (e:Exception){
            e.printStackTrace()
        }
    }
      */
    private fun postArtsList(){
        val postService=ServiceCreator.create(PostService::class.java)//获取动态代理
        postService.listPostOfArts().enqueue(object : Callback<ResponseFromServer>{
            override fun onResponse(
                call: Call<ResponseFromServer>,
                response: Response<ResponseFromServer>
            ) {
                Log.d("MainActivity","message is "+response.body()?.code)
                postBList.addAll(response.body()?.items!!)
            }

            override fun onFailure(call: Call<ResponseFromServer>, t: Throwable) {
                t.printStackTrace()
            }

        })
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



