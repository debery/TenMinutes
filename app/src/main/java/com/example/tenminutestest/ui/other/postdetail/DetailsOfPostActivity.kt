package com.example.tenminutestest.ui.other.postdetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tenminutestest.BaseActivity
import com.example.tenminutestest.MyApplication
import com.example.tenminutestest.R
import com.example.tenminutestest.User_IO
import com.example.tenminutestest.logic.model.*
import com.example.tenminutestest.logic.network.CommentService
import com.example.tenminutestest.logic.network.ServiceCreator
import com.example.tenminutestest.ui.other.ImageShowActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DetailsOfPostActivity:BaseActivity() {

    private var commentList:List<Comment>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        val post=intent.getSerializableExtra("post_data") as PostB
        initPost(post)

        getReply(post)//获取帖子的回复
        //回复的点击事件
        val btnReply: Button =findViewById(R.id.btnReplyPost)
        btnReply.setOnClickListener {
            popupWindowToReply(post)
        }
//      获取recyclerView点击item传入的帖子数据



    }
    //弹出回复帖子的popupWindow
    private fun popupWindowToReply(post: PostB){
        val contentView= LayoutInflater.from(this).inflate(R.layout.pop_reply,null)
        val popupWindow= PopupWindow(contentView,
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.isFocusable=true
        popupWindow.isOutsideTouchable=true
        popupWindow.isTouchable=true

        popupWindow.showAtLocation(contentView, Gravity.BOTTOM,0,0)

        val editReply: EditText =contentView.findViewById(R.id.editForReply)
        editReply.requestFocus()
        editReply.isFocusable=true
        editReply.isFocusableInTouchMode=true

        val timer = Timer() //设置定时器，因为需要时间等待popup的布局加载完成才
        timer.schedule(object : TimerTask() {
            override fun run() { //弹出软键盘的代码
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(editReply, InputMethodManager.RESULT_SHOWN)
                imm.toggleSoftInput(
                    InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY
                )
            }
        }, 200) //设置200毫秒的时长
        val addBtn:Button=contentView.findViewById(R.id.btnForReply)
        addBtn.setOnClickListener {
            addComment(post,editReply.text.toString())
            popupWindow.dismiss()
        }

    }

    private fun addComment(post: PostB, commentText:String){


        val id=post.post_id

        if(commentText.isNotEmpty()){
            if(User_IO.get_userinfos(this)!=null){
                val nickname=User_IO.get_userinfos(this)[2]
                val com=CommentUp(id,commentText,nickname)
                val service=ServiceCreator.create(CommentService::class.java)
                service.addCommentOfArts(com).enqueue(object :Callback<CommentResponse>{
                    override fun onResponse(
                        call: Call<CommentResponse>,
                        response: Response<CommentResponse>
                    ) {

                        Log.d("addCommentSuccess", response.isSuccessful.toString())
                        Log.d("addCommentSuccess",response.body()?.success.toString())
                        Log.d("addCommentCode",response.body()?.code.toString())
                        Log.d("addCommentMessage",response.message().toString()+"无")

                        if(response.isSuccessful){
                            Toast.makeText(MyApplication.context,"发送成功",Toast.LENGTH_SHORT).show()
                            getReply(post)
                        }

                    }

                    override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                        t.printStackTrace()
                    }

                })
            }else{
                Toast.makeText(this,"找不到用户，请先登录",Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(this,"评论不可为空",Toast.LENGTH_SHORT).show()
            Log.d("Details.addComment",commentText)
        }


    }

    private fun initPost(post: PostB){
        //头像，昵称，帖子标题以及内容与一张图片
        val avatar: ImageView = findViewById(R.id.userHeadImage)
        val nickname: TextView = findViewById(R.id.userNameText)
        val postTitle: TextView =findViewById(R.id.userPostTitle)
        val content: TextView = findViewById(R.id.userCommentTxt)
        val time:TextView=findViewById(R.id.time)
        val pictureSingle: ImageView = findViewById(R.id.userCommentImage)

        //显示图片的布局，多图情况下操作其出现，单图保持gone
        val pictureLayoutFirst: LinearLayout =findViewById(R.id.pictureLayoutFirst)
        val pictureLayoutSecond: LinearLayout =findViewById(R.id.pictureLayoutSecond)

        //多图的位置
        val pictureItem1 : ImageView =findViewById(R.id.pictureItem1)
        val pictureItem2 : ImageView =findViewById(R.id.pictureItem2)
        val pictureItem3 : ImageView =findViewById(R.id.pictureItem3)
        val pictureItem4 : ImageView =findViewById(R.id.pictureItem4)
        val pictureItem5 : ImageView =findViewById(R.id.pictureItem5)
        val pictureItem6 : ImageView =findViewById(R.id.pictureItem6)

        //帖子下方的，用于监听
        val agreedLayout: LinearLayout =findViewById(R.id.agreedLayout)
        val commentLayout: LinearLayout =findViewById(R.id.commentLayout)
        val goodIcon:Button=findViewById(R.id.agreeIcon)
        val goodText: TextView =findViewById(R.id.dianzanTextView)
        val commentIcon:Button=findViewById(R.id.commentIcon)
        val commentText:TextView=findViewById(R.id.commentText)

        nickname.text=post.nickname
        //if(post.user_avatar!=null)Glide.with(MyApplication.context).load(post.user_avatar).into(holder.userAvatar)
        postTitle.text=post.post_title
        content.text=post.content
        time.text=post.time
        if(post.goods!=null)
            goodText.text="${post.goods}"
        if(post.replys!=null)
            commentText.text="${post.replys}"
        if(post.picture_1!=null&&post.picture_2==null) {
            pictureSingle.visibility= View.VISIBLE
            Glide.with(pictureSingle.context).load(post.picture_1).thumbnail(0.2f)
                .into(pictureSingle)
        }else{
            pictureLayoutFirst.visibility= View.GONE
        }
        //多图代码
        if(post.picture_2!=null){
            pictureLayoutFirst.visibility= View.VISIBLE//第一个图片布局显示，可放置三张
            Glide.with(pictureItem1.context).load(post.picture_1).thumbnail(0.2f)
                .into(pictureItem1)
            Glide.with(pictureItem2.context).load(post.picture_2).thumbnail(0.2f)
                .into(pictureItem2)
            if(post.picture_3!=null){
                Glide.with(pictureItem3.context).load(post.picture_3).thumbnail(0.2f)
                    .into(pictureItem3)
            }else{
                pictureItem3.visibility= View.INVISIBLE
            }
            if(post.picture_4!=null){
                pictureLayoutSecond.visibility= View.VISIBLE//第二个布局显示
                Glide.with(pictureItem4.context).load(post.picture_4).thumbnail(0.2f)
                    .into(pictureItem4)
                if(post.picture_5!=null){
                    Glide.with(pictureItem5.context).load(post.picture_5).thumbnail(0.2f)
                        .into(pictureItem5)
                }else{
                    pictureItem5.visibility= View.INVISIBLE
                }
                if(post.picture_6!=null){
                    Glide.with(pictureItem6.context).load(post.picture_6).thumbnail(0.2f)
                        .into(pictureItem6)
                }else{
                    pictureItem6.visibility= View.INVISIBLE
                }
            }else{
                pictureLayoutSecond.visibility= View.GONE
            }
        }else{
            pictureLayoutFirst.visibility= View.GONE
        }
        //点击监听
        agreedLayout.setOnClickListener{
            /* 待修改，应设置为每个用户只生效1次*/
            goodIcon.setBackgroundResource(
                R.drawable.dianzan_fill
            )
            goodText.text="${post.goods}"
        }
        goodIcon.setOnClickListener{
            /* 待修改，应设置为每个用户只生效1次*/
            goodIcon.setBackgroundResource(
                R.drawable.dianzan_fill
            )
            goodText.text="${post.goods}"
        }
        commentLayout.setOnClickListener {
            popupWindowToReply(post)
        }
        commentIcon.setOnClickListener {
            popupWindowToReply(post)
        }

        //图片点击显示
        pictureSingle.setOnClickListener{
            clickPicture(post,0)
        }
        pictureItem1.setOnClickListener {
            clickPicture(post,0)
        }
        pictureItem2.setOnClickListener {
            clickPicture(post,1)
        }
        pictureItem3.setOnClickListener {
            clickPicture(post,2)
        }
        pictureItem4.setOnClickListener {
            clickPicture(post,3)
        }
        pictureItem5.setOnClickListener {
            clickPicture(post,4)
        }
        pictureItem6.setOnClickListener {
            clickPicture(post,5)
        }
    }

    private fun clickPicture(post: PostB, i:Int){
        val intent = Intent(MyApplication.context, ImageShowActivity::class.java)
        intent.putExtra("post_data_2", post)
        intent.putExtra("show_position",i)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        MyApplication.context.startActivity(intent)
    }

    private fun getReply(post: PostB){

        val recyclerView:RecyclerView=findViewById(R.id.commentRecycler)
        recyclerView.layoutManager= LinearLayoutManager(this)
        val api=ServiceCreator.create(CommentService::class.java)
        api.listCommentOfArts(post).enqueue(object :Callback<CommentResponse>{
            override fun onResponse(
                call: Call<CommentResponse>,
                response: Response<CommentResponse>
            ) {
                Log.d("getReplySuccess",response.isSuccessful.toString())
                Log.d("getReplyCode",response.body()?.code.toString())
                if(response.isSuccessful){
                    commentList=response.body()?.items
                    Log.d("getReply",commentList.toString())
                    runOnUiThread {
                        if(commentList!=null){
                            commentList?.reversed()
                            recyclerView.adapter= CommentAdapter(commentList!!)
                        }//没有回复的帖子不绑定适配器，以免崩溃
                    }
                }
            }

            override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })

    }
}