package com.example.tenminutestest2.ui.main.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tenminutestest2.MyApplication
import com.example.tenminutestest2.R
import com.example.tenminutestest2.logic.model.Post
import com.example.tenminutestest2.ui.other.PictureShowActivity
import com.example.tenminutestest2.ui.other.PostDetail.PostDetailsActivity

class PostAdapter(val postList: List<Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>() {


    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        //头像，昵称，帖子标题以及内容与单图片
        val userHeadImage: ImageView = view.findViewById(R.id.userHeadImage)
        val userNameText: TextView = view.findViewById(R.id.userNameText)
        val userPostTitle: TextView=view.findViewById(R.id.userPostTitle)
        val userCommentText: TextView = view.findViewById(R.id.userCommentTxt)
        val userCommentImage: ImageView = view.findViewById(R.id.userCommentImage)

        //显示图片的布局，多图情况下操作其出现，单图保持gone
        val pictureLayoutFirst:LinearLayout=view.findViewById(R.id.pictureLayoutFirst)
        val pictureLayoutSecond:LinearLayout=view.findViewById(R.id.pictureLayoutSecond)

        //多图的位置
        val pictureItem1 :ImageView=view.findViewById(R.id.pictureItem1)
        val pictureItem2 :ImageView=view.findViewById(R.id.pictureItem2)
        val pictureItem3 :ImageView=view.findViewById(R.id.pictureItem3)
        val pictureItem4 :ImageView=view.findViewById(R.id.pictureItem4)
        val pictureItem5 :ImageView=view.findViewById(R.id.pictureItem5)
        val pictureItem6 :ImageView=view.findViewById(R.id.pictureItem6)

        //帖子下方的图片，用于监听
        val agreedLayout:LinearLayout=view.findViewById(R.id.agreedLayout)
        val commentLayout:LinearLayout=view.findViewById(R.id.commentLayout)
        val agreedIcon:Button=view.findViewById(R.id.agreeIcon)
        val dianzan:TextView=view.findViewById(R.id.dianzanTextView)
        val commentIcon:Button=view.findViewById(R.id.commentIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_post,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //基本的显示
        val post=postList[position]
        holder.userNameText.text=post.userName
        holder.userHeadImage.setImageResource(post.userHeadId)
        holder.userPostTitle.text=post.postTitle
        holder.userCommentText.text=post.commentText
        if(post.agreedCount!=0)holder.dianzan.text="${post.agreedCount}"
        if(post.commentImage1!=0&&post.commentImage2==0) {
            holder.userCommentImage.visibility=View.VISIBLE
            holder.userCommentImage.setImageResource(post.commentImage1)
        }
        //多图代码
        if(post.commentImage2!=0){
            holder.pictureLayoutFirst.visibility=View.VISIBLE//第一个图片布局显示，可放置三张
            holder.pictureItem1.setImageResource(post.commentImage1)
            holder.pictureItem2.setImageResource(post.commentImage2)
            if(post.commentImage3!=0)holder.pictureItem3.setImageResource(post.commentImage3)
            if(post.commentImage4!=0){
                holder.pictureLayoutSecond.visibility=View.VISIBLE//第二个布局显示
                holder.pictureItem4.setImageResource(post.commentImage4)
                if(post.commentImage5!=0)holder.pictureItem5.setImageResource(post.commentImage5)
                if(post.commentImage6!=0)holder.pictureItem6.setImageResource(post.commentImage6)
            }
        }

        //点击监听

        holder.itemView.setOnClickListener {
            val intent = Intent(MyApplication.context, PostDetailsActivity::class.java)
            intent.putExtra("post_data", post)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            MyApplication.context.startActivity(intent)
        }

        holder.agreedLayout.setOnClickListener{
            post.agreedCount++/* 待修改，应设置为每个用户只生效1次*/
            holder.agreedIcon.setBackgroundResource(
                R.drawable.dianzan_fill
            )
            holder.dianzan.text="${post.agreedCount}"
        }
        holder.agreedIcon.setOnClickListener{
            post.agreedCount++/* 待修改，应设置为每个用户只生效1次*/
            holder.agreedIcon.setBackgroundResource(
                R.drawable.dianzan_fill
            )
            holder.dianzan.text="${post.agreedCount}"
        }

        //图片点击显示
        holder.userCommentImage.setOnClickListener{
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

    }

    override fun getItemCount() = postList.size

    private fun clickPicture(post: Post, i:Int){
        val intent = Intent(MyApplication.context, PictureShowActivity::class.java)
        intent.putExtra("post_data", post)
        intent.putExtra("show_position",i)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        MyApplication.context.startActivity(intent)
    }
}
