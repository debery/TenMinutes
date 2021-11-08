package com.example.tenminutestest2


import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class PostDetailsActivity:AppCompatActivity() {

    private var replyList:ArrayList<Reply>?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)
        supportActionBar?.hide()
        //加载帖子与回复
        initPostAndReply()
        //回复的点击事件
        val btnReply:Button=findViewById(R.id.btnReplyPost)
        btnReply.setOnClickListener {
            popupWindowToReply()
        }
    }

    private fun initPostAndReply(){
        val post=intent.getSerializableExtra("post_data") as Post
        initPost(post)
        replyList=post.replyList

        val replyRecyclerView:RecyclerView=findViewById(R.id.postReplyRecycler)
        replyRecyclerView.layoutManager=LinearLayoutManager(this)
        if(replyList!=null){
            replyRecyclerView.adapter=ReplyAdapter(replyList!!)
        }//没有回复的帖子不绑定适配器，以免崩溃
    }

    //弹出回复帖子的popupWindow
    private fun popupWindowToReply(){
        val contentView=LayoutInflater.from(this).inflate(R.layout.add_post_reply_pop,null)
        val popupWindow=PopupWindow(contentView,
            ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.isFocusable=true
        popupWindow.isOutsideTouchable=true
        popupWindow.isTouchable=true

        popupWindow.showAtLocation(contentView, Gravity.BOTTOM,0,0)

        val editReply:EditText=contentView.findViewById(R.id.editForReply)
        editReply.requestFocus()
        editReply.isFocusable=true
        editReply.isFocusableInTouchMode=true

        val timer = Timer() //设置定时器
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

    }

    private fun initPost(post:Post){
        //头像，昵称，帖子标题以及内容与一张图片
        val userHeadImage: ImageView = findViewById(R.id.userHeadImage)
        val userNameText: TextView = findViewById(R.id.userNameText)
        val userPostTitle: TextView =findViewById(R.id.userPostTitle)
        val userCommentText: TextView = findViewById(R.id.userCommentTxt)
        val userCommentImage: ImageView = findViewById(R.id.userCommentImage)

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

        //帖子下方的图片，用于监听
        val agreedIcon:Button=findViewById(R.id.agreeIcon)
        val dianzan: TextView =findViewById(R.id.dianzanTextView)
        val commentIcon:Button=findViewById(R.id.commentIcon)


        userNameText.text=post.userName
        userHeadImage.setImageResource(post.userHeadId)
        userPostTitle.text=post.postTitle
        userCommentText.text=post.commentText
        if(post.commentImage1!=0&&post.commentImage2==0) {
            userCommentImage.visibility=View.VISIBLE
            userCommentImage.setImageResource(post.commentImage1)
        }
        //多图代码
        if(post.commentImage2!=0){
            pictureLayoutFirst.visibility= View.VISIBLE
            pictureItem1.setImageResource(post.commentImage1)
            pictureItem2.setImageResource(post.commentImage2)
            if(post.commentImage3!=0) pictureItem3.setImageResource(post.commentImage3)
            if(post.commentImage4!=0){
                pictureLayoutSecond.visibility= View.VISIBLE
                pictureItem4.setImageResource(post.commentImage4)
                if(post.commentImage5!=0)pictureItem5.setImageResource(post.commentImage5)
                if(post.commentImage6!=0)pictureItem6.setImageResource(post.commentImage6)
            }
        }
    }
}
