package com.example.tenminutestest.ui.main.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tenminutestest.R
import com.example.tenminutestest.logic.model.PostB
import com.example.tenminutestest.logic.model.ListPostResponse
import com.example.tenminutestest.logic.model.ListPostRequire
import com.example.tenminutestest.logic.network.PostService
import com.example.tenminutestest.logic.network.ServiceCreator
import com.example.tenminutestest.ui.main.ShowButton
import com.example.tenminutestest.ui.main.MainActivity
import com.example.tenminutestest.ui.main.adapter.GeneralPostAdapter
import com.example.tenminutestest.ui.other.NoticeActivity
import com.example.tenminutestest.ui.other.SearchActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ArtsTabFragment: Fragment() {

    private val postList=ArrayList<PostB>()

    private val ARTS=2

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
            ShowButton(2).showAddWindow(activity)
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
        val adapter = GeneralPostAdapter(postList,ARTS)
        recycler?.adapter=adapter

        val btn: Button? = view?.findViewById(R.id.button)
        btn?.setOnClickListener {
            postArtsList()
            MainActivity().runOnUiThread {
                recycler?.adapter=adapter
            }

        }
    }


    private fun postArtsList(){
        val postService=ServiceCreator.create(PostService::class.java)//获取动态代理
        postService.getPosts(ListPostRequire("post_arts")).enqueue(object : Callback<ListPostResponse>{
            override fun onResponse(
                call: Call<ListPostResponse>,
                response: Response<ListPostResponse>
            ) {
                Log.d("post arts list","success is "+response.isSuccessful)
                val list=response.body()?.items
                if (list != null) {

                    postList.clear()
                    postList.addAll(response.body()?.items!!)
                    postList.reverse()
                    MainActivity().runOnUiThread {
                        val recyclerView:RecyclerView=view?.findViewById(R.id.recycler)!!
                        recyclerView.layoutManager=LinearLayoutManager(activity)
                        recyclerView.adapter=GeneralPostAdapter(postList,ARTS)
                    }
                }
            }

            override fun onFailure(call: Call<ListPostResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    //暂时缺乏获取用户信息
    private fun fromPostToPostB(){
        for(post in postList){
            post.userid
            //根据userid获取头像昵称
            post.user_avatar
            post.nickname
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data?.getBooleanExtra("send",false)==true){
            postArtsList()
        }
    }

}



