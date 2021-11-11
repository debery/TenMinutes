package com.example.tenminutestest2


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private var postBList:ArrayList<PostB>?=null

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
        val recycler:RecyclerView?=view?.findViewById(R.id.recycler)
        recycler?.layoutManager=LinearLayoutManager(activity)
        if(postBList!=null)recycler?.adapter=GeneralPostAdapter(postBList!!)
    }

    private fun zanshi(){
        postBList?.add(PostB(1000001,"测试员罢了",post_title = "1111",content = "1122"))
    }

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

    private fun parseJSON(jsonData:String){
        val gson= Gson()
        val typeOf=object : TypeToken<List<PostB>>(){}.type
        postBList=gson.fromJson<ArrayList<PostB>>(jsonData,typeOf)
    }

}



