package com.example.tenminutestest2

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GeneralPostAdapter(val postList:List<PostB>):RecyclerView.Adapter<GeneralPostAdapter.ViewHolder>() {

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
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GeneralPostAdapter.ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.post_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GeneralPostAdapter.ViewHolder, position: Int) {
        val post=postList[position]
        holder.nickname.text=post.nickname
        //if(post.user_avatar!=null)Glide.with(MyApplication.context).load(Uri.parse(post.user_avatar)).into(holder.userAvatar)
        holder.postTitle.text=post.post_title
        holder.content.text=post.content
    }

    override fun getItemCount()=postList.size



}