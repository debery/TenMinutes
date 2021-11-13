package com.example.tenminutestest2


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
            val postFromServer =gson.fromJson(jsonData,PostFromServer::class.java)
            //val list=gson.fromJson<List<PostB>>(postFromServer.items.toString(),object :TypeToken<List<PostB>>(){}.type)
            postBList.add(postFromServer.items.elementAt(0))
            postBList.add(postFromServer.items.elementAt(1))
            Log.d("wwwwwww","${postFromServer.success}")
            Log.d("wwwwwww","${postFromServer.items}")
            Log.d("wwwwwww","${PostB(1000000,"11111",post_title = "1111111",content = "22222")}")
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

}



