package com.example.tenminutestest.ui.main.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
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
import com.example.tenminutestest.ui.other.AddPostActivity
import com.example.tenminutestest.ui.other.NoticeActivity
import com.example.tenminutestest.ui.other.SearchActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeachTabFragment: Fragment() {

    private val postList=ArrayList<PostB>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_teach, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val btnSearch: Button? =view?.findViewById(R.id.btnSearch)
        val btnAddFolder: Button? =view?.findViewById(R.id.addFolder)
        val btnNotice: Button? =view?.findViewById(R.id.notice)

        val contentView= LayoutInflater.from(activity).inflate(R.layout.pop_create_post,null)
        val btnAdd: Button =contentView.findViewById(R.id.btnAdd)
        btnAddFolder?.setOnClickListener {
            AddButton(1).showWindow(activity)
        }
        btnNotice?.setOnClickListener {
            val intent = Intent(activity, NoticeActivity::class.java)
            startActivity(intent)
        }
        btnSearch?.setOnClickListener {
            val intent=Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }
        postTeachingList()
        val recyclerView:RecyclerView?=view?.findViewById(R.id.recycler)
        recyclerView?.layoutManager=LinearLayoutManager(activity)
        val adapter=GeneralPostAdapter(postList)
        recyclerView?.adapter= adapter

        val btn: Button? = view?.findViewById(R.id.button)
        btn?.setOnClickListener {
            postTeachingList()
            MainActivity().runOnUiThread {
                recyclerView?.adapter=adapter
            }
            Log.d("btnTeaching",postList.toString())
        }
    }

    private fun postTeachingList(){
        val postService= ServiceCreator.create(PostService::class.java)//获取动态代理
        postService.listPostOfTeaching().enqueue(object : Callback<PostResponse> {
            override fun onResponse(
                call: Call<PostResponse>,
                response: Response<PostResponse>
            ) {
                Log.d("post teaching list","successful is "+response.isSuccessful)
                val list=response.body()?.items
                Log.d("internet,post",list.toString())
                if (list != null) {
                    for(post in list){
                        Log.d("picture","nickname is "+post.nickname)
                        Log.d("picture","picture is "+post.picture_1)
                    }
                    postList.clear()
                    postList.addAll(response.body()?.items!!)
                    postList.reverse()
                    MainActivity().runOnUiThread {
                        val recyclerView:RecyclerView=view?.findViewById(R.id.recycler)!!
                        recyclerView.layoutManager=LinearLayoutManager(activity)
                        recyclerView.adapter=GeneralPostAdapter(postList)
                    }
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data?.getBooleanExtra("send",false)==true){
            Log.d("after add",postList.toString())
            postTeachingList()
        }
    }
}