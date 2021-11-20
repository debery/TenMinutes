package com.example.tenminutestest.ui.main


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
import com.example.tenminutestest.R
import com.example.tenminutestest.logic.model.PostB
import com.example.tenminutestest.logic.model.ResponseFromServer
import com.example.tenminutestest.logic.network.PostService
import com.example.tenminutestest.logic.network.ServiceCreator
import com.example.tenminutestest.ui.main.adapter.GeneralPostAdapter
import com.example.tenminutestest.ui.other.AddPostActivity
import com.example.tenminutestest.ui.other.NoticeActivity
import com.example.tenminutestest.ui.other.SearchActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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



    private fun postArtsList(){
        val postService=ServiceCreator.create(PostService::class.java)//获取动态代理
        postService.listPostOfArts().enqueue(object : Callback<ResponseFromServer>{
            override fun onResponse(
                call: Call<ResponseFromServer>,
                response: Response<ResponseFromServer>
            ) {
                Log.d("MainActivity","message is "+response.body()?.code)
                val list=response.body()?.items
                if (list != null) {
                    for(post in list){
                        Log.d("picture","nickname is "+post.nickname)
                        Log.d("picture","picture is "+post.picture_1)
                    }
                }
                postBList.addAll(response.body()?.items!!)
                postBList.reverse()

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



