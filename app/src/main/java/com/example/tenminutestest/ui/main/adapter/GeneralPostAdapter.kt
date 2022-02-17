package com.example.tenminutestest.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import cn.jzvd.JzvdStd
import com.bumptech.glide.Glide
import com.example.tenminutestest.MyApplication
import com.example.tenminutestest.R
import com.example.tenminutestest.logic.model.PostB
import com.example.tenminutestest.logic.touse.GoodUse
import com.example.tenminutestest.ui.other.ImageShowActivity
import com.example.tenminutestest.ui.other.postdetail.DetailsOfPostActivity
import com.example.tenminutestest.util.User_IO


class GeneralPostAdapter(private val postList:List<PostB>,private val flag:Int):
    RecyclerView.Adapter<GeneralPostAdapter.ViewHolder>() {

    /*该适配器将实现三个界面的不同帖子控件，目前只实现艺术板块，待日后修改
    * 2021.11.11
    * */

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        //头像，昵称，帖子标题以及内容与单图片
        val userAvatar: ImageView = view.findViewById(R.id.userHeadImage)
        val nickname: TextView = view.findViewById(R.id.userNameText)
        val postTitle: TextView =view.findViewById(R.id.userPostTitle)
        val content: TextView = view.findViewById(R.id.userCommentTxt)
        val pictureSingle: ImageView = view.findViewById(R.id.userCommentImage)
        val time:TextView=view.findViewById(R.id.time)

        //显示图片的布局，多图情况下操作其出现，单图保持gone
        val pictureLayoutFirst: LinearLayout =view.findViewById(R.id.pictureLayoutFirst)
        val pictureLayoutSecond: LinearLayout =view.findViewById(R.id.pictureLayoutSecond)

        //多图的位置
        val pictureItem1 : ImageView =view.findViewById(R.id.pictureItem1)
        val pictureItem2 : ImageView =view.findViewById(R.id.pictureItem2)
        val pictureItem3 : ImageView =view.findViewById(R.id.pictureItem3)
        val pictureItem4 : ImageView =view.findViewById(R.id.pictureItem4)
        val pictureItem5 : ImageView =view.findViewById(R.id.pictureItem5)
        val pictureItem6 : ImageView =view.findViewById(R.id.pictureItem6)

        //帖子下方的图片，用于监听
        val agreedLayout: LinearLayout =view.findViewById(R.id.agreedLayout)
        val commentLayout: LinearLayout =view.findViewById(R.id.commentLayout)
        val agreedIcon: Button =view.findViewById(R.id.agreeIcon)
        val dianzan: TextView =view.findViewById(R.id.dianzanTextView)
        val commentIcon: Button =view.findViewById(R.id.commentIcon)

        //视频
        val videoView:JzvdStd=view.findViewById(R.id.video)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_post,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val post=postList[position]
        holder.nickname.text=post.nickname
        if(post.user_avatar!=null&&post.user_avatar!="")
            Glide.with(MyApplication.context).load(post.user_avatar).into(holder.userAvatar)
        holder.postTitle.text=post.post_title
        holder.content.text=post.content
        holder.time.text=post.time
        if(post.goods!=null)holder.dianzan.text="${post.goods}"
        if(post.picture_1!=null&&post.picture_2==null) {
            holder.pictureSingle.visibility=View.VISIBLE
            Glide.with(holder.pictureSingle.context).load(post.picture_1).thumbnail(0.2f)
                    .into(holder.pictureSingle)
        }else{
            holder.pictureLayoutFirst.visibility=View.GONE
        }
        //多图代码
        if(post.picture_2!=null){
            holder.pictureLayoutFirst.visibility=View.VISIBLE//第一个图片布局显示，可放置三张
            Glide.with(holder.pictureItem1.context).load(post.picture_1).thumbnail(0.2f)
                .into(holder.pictureItem1)
            Glide.with(holder.pictureItem2.context).load(post.picture_2).thumbnail(0.2f)
                .into(holder.pictureItem2)
            if(post.picture_3!=null){
                Glide.with(holder.pictureItem3.context).load(post.picture_3).thumbnail(0.2f)
                    .into(holder.pictureItem3)
            }else{
                holder.pictureItem3.visibility=View.INVISIBLE
            }
            if(post.picture_4!=null){
                holder.pictureLayoutSecond.visibility=View.VISIBLE//第二个布局显示
                Glide.with(holder.pictureItem4.context).load(post.picture_4).thumbnail(0.2f)
                    .into(holder.pictureItem4)
                if(post.picture_5!=null){
                    Glide.with(holder.pictureItem5.context).load(post.picture_5).thumbnail(0.2f)
                        .into(holder.pictureItem5)
                }else{
                    holder.pictureItem5.visibility=View.INVISIBLE
                }
                if(post.picture_6!=null){
                    Glide.with(holder.pictureItem6.context).load(post.picture_6).thumbnail(0.2f)
                        .into(holder.pictureItem6)
                }else{
                    holder.pictureItem6.visibility=View.INVISIBLE
                }
            }else{
                holder.pictureLayoutSecond.visibility=View.GONE
            }
        }else{
            holder.pictureLayoutFirst.visibility=View.GONE
        }
        //点击监听
        holder.agreedLayout.setOnClickListener{
            val target:String=when(flag){
                1->"post_teaching"
                2->"post_arts"
                3->"post_sport"
                else->"error"
            }
            //
            val user=User_IO.get_userinfos(MyApplication.context)
            if(target!="error"){
                GoodUse(post.post_id.toString(),user[0],"post_goods",target).add()
                holder.agreedIcon.setBackgroundResource(
                    R.drawable.dianzan_fill
                )
                val goods= post.goods?.toInt()?.plus(1).toString()
                holder.dianzan.text=goods
            }else{
                Toast.makeText(MyApplication.context,"点赞出错了，请重试",Toast.LENGTH_SHORT).show()
            }

        }
        holder.agreedIcon.setOnClickListener{
            val target:String=when(flag){
                1->"post_teaching"
                2->"post_arts"
                3->"post_sport"
                else->"error"
            }
            //
            val user=User_IO.get_userinfos(MyApplication.context)
            if(target!="error"){
                GoodUse(post.post_id.toString(),user[0],"post_goods",target).add()
                holder.agreedIcon.setBackgroundResource(
                    R.drawable.dianzan_fill
                )
                val goods= post.goods?.toInt()?.plus(1).toString()
                holder.dianzan.text=goods
            }else{
                Toast.makeText(MyApplication.context,"点赞出错了，请重试",Toast.LENGTH_SHORT).show()
            }
        }

        Log.d("adapter/149l",post.userid+"  "+post.videos.toString()+"  "+post.goods)


        if(post.videos!=null&&post.videos!=""&&post.videos!="String") {
//            BeforeUseSomeCode.doIt()
            holder.videoView.visibility=View.VISIBLE
            holder.videoView.setUp(post.videos,"",JzvdStd.SCREEN_NORMAL)
            holder.videoView.positionInList=position
            Glide.with(MyApplication.context).load(R.drawable.maid).into(holder.videoView.thumbImageView)

        }
            //初始化
//            IjkMediaPlayer.loadLibrariesOnce(null)
//            IjkMediaPlayer.native_profileBegin("libijkplayer.so")
//            holder.videoView.visibility = View.VISIBLE
//            //监听
//            holder.videoView.setListener(object : IjkVideoView.VideoPlayerListener() {
//                override fun onPrepared(p0: IMediaPlayer?) {
//                    Log.d("onPrepare", "执行")
//                    p0?.start()
//                    holder.videoView.visibility = View.VISIBLE
//                }
//
//                override fun onCompletion(p0: IMediaPlayer?) {
//                    Log.d("adapter", "onCompletion")
//                }
//
//                override fun onError(p0: IMediaPlayer?, p1: Int, p2: Int): Boolean {
//                    Toast.makeText(MyApplication.context, "播放失败", Toast.LENGTH_SHORT).show()
//                    return false
//                }
//            })
//
//            holder.videoView.setPath(post.videos)


//            BeforeUseSomeCode.doIt()
//            holder.videoView.visibility=View.VISIBLE
//            val setup=holder.videoView.setUp(post.videos, JCVideoPlayer.SCREEN_LAYOUT_LIST,"")
//            if(setup){
//                Glide.with(MyApplication.context).load(R.drawable.jc_loading).into(holder.videoView.thumbImageView)
//            }
//        }
//        if(post.videos!=null&&post.videos!=""&&post.videos!="String"){
//            holder.videoView.visibility=View.VISIBLE
//            holder.videoView.setVideoURI(Uri.parse(post.videos))
//            holder.videoView.setMediaController(MediaController(MyApplication.context))
//        }

        //图片点击显示
        holder.pictureSingle.setOnClickListener{
            clickPicture(post,0)
        }
        holder.pictureItem1.setOnClickListener {
            clickPicture(post,0)
        }
        holder.pictureItem2.setOnClickListener {
            clickPicture(post,1)
        }
        holder.pictureItem3.setOnClickListener {
            clickPicture(post,2)
        }
        holder.pictureItem4.setOnClickListener {
            clickPicture(post,3)
        }
        holder.pictureItem5.setOnClickListener {
            clickPicture(post,4)
        }
        holder.pictureItem6.setOnClickListener {
            clickPicture(post,5)
        }

        /*
        holder.itemView.setOnClickListener {
            val intent = Intent(MyApplication.context, PostDetailsActivity::class.java)
            intent.putExtra("post_data", post)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            MyApplication.context.startActivity(intent)
        }

         */
        //子项监听
        holder.itemView.setOnClickListener {
            val intent = Intent(MyApplication.context, DetailsOfPostActivity::class.java)
            intent.putExtra("post_data", post)
            intent.putExtra("flag",flag)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            MyApplication.context.startActivity(intent)
        }
//        holder.itemView.setOnClickListener {
//            val api=ServiceCreator.create(PostService::class.java)
//            api.deletePostOfArts(post).enqueue(object :retrofit2.Callback<ResponseFromServer>{
//                override fun onResponse(
//                    call: Call<ResponseFromServer>,
//                    response: Response<ResponseFromServer>
//                ) {
//                    if(response.isSuccessful)Toast.makeText(MyApplication.context,"删除成功",Toast.LENGTH_SHORT).show()
//                    Log.d("message",response.body()?.message.toString())
//                    Log.d("message",response.message().toString())
//                    Log.d("success",response.isSuccessful.toString())
//                    Log.d("success",response.body()?.success.toString())
//                }
//
//                override fun onFailure(call: Call<ResponseFromServer>, t: Throwable) {
//                    Log.d("error",call.isExecuted.toString())
//                    t.printStackTrace()
//                }
//
//            })
//        }



    }


    override fun onViewRecycled(holder: ViewHolder) {
        val listHolder= listOf<View>(holder.pictureSingle,
            holder.pictureItem1,holder.pictureItem2,holder.pictureItem3,
            holder.pictureItem4,holder.pictureItem5,holder.pictureItem6)
        for(item in listHolder){
            Glide.clear(item)
        }
        super.onViewRecycled(holder)
    }

    override fun getItemCount()=postList.size

    private fun clickPicture(post: PostB, i:Int){
        val intent = Intent(MyApplication.context, ImageShowActivity::class.java)
        intent.putExtra("post_data_2", post)
        intent.putExtra("show_position",i)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        MyApplication.context.startActivity(intent)
    }


}