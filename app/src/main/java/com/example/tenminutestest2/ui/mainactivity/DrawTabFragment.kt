package com.example.tenminutestest2.ui.mainactivity


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tenminutestest2.R
import com.example.tenminutestest2.logic.model.PostB
import com.example.tenminutestest2.logic.model.ResponseFromServer
import com.example.tenminutestest2.ui.mainactivity.adapter.GeneralPostAdapter
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
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

        return inflater.inflate(R.layout.draw_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        postArtsList()
        val recycler: RecyclerView? = view?.findViewById(R.id.recycler)
        recycler?.layoutManager = LinearLayoutManager(activity)
        val adapter = GeneralPostAdapter(postBList)
        recycler?.adapter=adapter

        val btn: Button? = view?.findViewById(R.id.button)
        btn?.setOnClickListener {
            postArtsList()
            adapter.notifyDataSetChanged()
        }


    }


    //使用okhttp调用接口
    private fun postArtsList(){
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

}



