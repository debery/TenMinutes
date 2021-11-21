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
import com.example.tenminutestest.logic.model.PostResponse
import com.example.tenminutestest.logic.network.PostService
import com.example.tenminutestest.logic.network.ServiceCreator
import com.example.tenminutestest.ui.main.AddButton
import com.example.tenminutestest.ui.main.MainActivity
import com.example.tenminutestest.ui.main.adapter.GeneralPostAdapter
import com.example.tenminutestest.ui.other.NoticeActivity
import com.example.tenminutestest.ui.other.SearchActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DrawTabFragment: Fragment() {

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
            AddButton().showWindow(activity)
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
            MainActivity().runOnUiThread {
                recycler?.adapter=adapter
                recycler?.adapter?.notifyDataSetChanged()
            }

        }
    }



    private fun postArtsList(){
        val postService=ServiceCreator.create(PostService::class.java)//获取动态代理
        postService.listPostOfArts().enqueue(object : Callback<PostResponse>{
            override fun onResponse(
                call: Call<PostResponse>,
                response: Response<PostResponse>
            ) {
                Log.d("MainActivity","message is "+response.body()?.code)
                val list=response.body()?.items
                if (list != null) {
                    for(post in list){
                        Log.d("picture","nickname is "+post.nickname)
                        Log.d("picture","picture is "+post.picture_1)
                    }
                }
                postBList.clear()
                postBList.addAll(response.body()?.items!!)
                postBList.reverse()

            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

}



