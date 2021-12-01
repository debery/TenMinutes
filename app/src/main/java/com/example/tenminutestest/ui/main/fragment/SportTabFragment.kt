package com.example.tenminutestest.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tenminutestest.R
import com.example.tenminutestest.logic.model.PostB
import com.example.tenminutestest.logic.model.PostResponse
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


class SportTabFragment:Fragment() {

    //private var videoUri:String?=null
    private val postList=ArrayList<PostB>()

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
            ShowButton(3).showAddWindow(activity)
        }
        btnNotice?.setOnClickListener {
            val intent = Intent(activity, NoticeActivity::class.java)
            startActivity(intent)
        }
        btnSearch?.setOnClickListener {
            val intent=Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }

        postSportList()
        val recyclerView:RecyclerView?=view?.findViewById(R.id.recycler)
        recyclerView?.layoutManager=LinearLayoutManager(activity)
        val adapter=GeneralPostAdapter(postList)
        recyclerView?.adapter= adapter

        val btn: Button? = view?.findViewById(R.id.button)
        btn?.setOnClickListener {
            postSportList()
            MainActivity().runOnUiThread {
                recyclerView?.adapter=adapter
            }
        }

//        val videoView: VideoView? = view?.findViewById(R.id.videoView)
//        videoView?.setMediaController(MediaController(activity))
//        val upload:Button? =view?.findViewById(R.id.upload)
//        upload?.setOnClickListener {
//            RequestInRun().verifyStoragePermissions(activity)
//            val intent=Intent( Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            intent.type="video/*"
//            startActivityForResult(intent,1)
//        }

//        val url = Uri.parse("android.resource://${activity?.packageName}/${R.raw.video}")
//        videoView?.setMediaController(MediaController(activity))
//        videoView?.setVideoURI(url)
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        val videoView: VideoView? = view?.findViewById(R.id.videoView)
//        videoView?.suspend()
//    }

    private fun postSportList(){
        val postService=ServiceCreator.create(PostService::class.java)//获取动态代理
        postService.listPostOfSport().enqueue(object : Callback<PostResponse>{
            override fun onResponse(
                call: Call<PostResponse>,
                response: Response<PostResponse>
            ) {
                Log.d("post sport list","success is "+response.isSuccessful)
                Log.d("post","item"+response.body()?.items)
                val list=response.body()?.items
                if (list != null) {
                    for(post in list){
                        Log.d("picture","nickname is "+post.nickname)
                        Log.d("picture","picture is "+post.picture_1)
                    }
                    postList.clear()
                    postList.addAll(response.body()?.items!!)
                    postList.reverse()
                    MainActivity().runOnUiThread {
                        val recyclerView: RecyclerView =view?.findViewById(R.id.recycler)!!
                        recyclerView.layoutManager= LinearLayoutManager(activity)
                        recyclerView.adapter= GeneralPostAdapter(postList)
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
            postSportList()
        }
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        val videoView: VideoView? = view?.findViewById(R.id.videoView)
//        when(requestCode){
//            1->{
//                if(resultCode== Activity.RESULT_OK && data!=null){
//                    val video=File(ContentUriUtil().getPath(MyApplication.context,data?.data!!))
//                    val fileService=ServiceCreator.create2(FileService::class.java)
//                    val requestFile= video.asRequestBody("video/mp4".toMediaTypeOrNull())
//                    val body= MultipartBody.Part.createFormData("file",video.name,requestFile)
//                    fileService.uploadFile(body).enqueue(object :Callback<FileResponse>{
//                        override fun onResponse(
//                            call: Call<FileResponse>,
//                            response: Response<FileResponse>
//                        ) {
//                            Toast.makeText(MyApplication.context,"上传成功",Toast.LENGTH_SHORT).show()
//                            videoUri=response.body()?.fileDownloadUri
//
//                            videoView?.setVideoURI(Uri.parse(videoUri))
//                            Toast.makeText(MyApplication.context,"设置播放成功",Toast.LENGTH_SHORT).show()
//
//                        }
//
//                        override fun onFailure(call: Call<FileResponse>, t: Throwable) {
//                            Toast.makeText(MyApplication.context,"上传失败",Toast.LENGTH_SHORT).show()
//                        }
//
//                    })
//                }
//
//            }
//        }
//    }
}