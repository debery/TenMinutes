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
import com.bumptech.glide.Glide
import com.example.tenminutestest2.MyApplication
import com.example.tenminutestest2.R
import com.example.tenminutestest2.logic.model.Post
import com.example.tenminutestest2.logic.model.PostB
import com.example.tenminutestest2.ui.other.PictureShowActivity
import com.example.tenminutestest2.ui.other.PostDetail.PostDetailsActivity

class GeneralPostAdapter(private val postList:List<PostB>):RecyclerView.Adapter<GeneralPostAdapter.ViewHolder>() {

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
    ): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_post,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post=postList[position]
        holder.nickname.text=post.nickname
        //if(post.user_avatar!=null)Glide.with(MyApplication.context).load(Uri.parse(post.user_avatar)).into(holder.userAvatar)
        holder.postTitle.text=post.post_title
        holder.content.text=post.content
        if(post.goods!=0)holder.dianzan.text="${post.goods}"
        if(post.picture_1!=null&&post.picture_2==null) {
            holder.pictureSingle.visibility=View.VISIBLE
            Glide.with(MyApplication.context).load(post.picture_1).into(holder.pictureSingle)
        }
        //多图代码
        if(post.picture_2!=null){
            holder.pictureLayoutFirst.visibility=View.VISIBLE//第一个图片布局显示，可放置三张
            Glide.with(MyApplication.context).load(post.picture_1).into(holder.pictureItem1)
            Glide.with(MyApplication.context).load(post.picture_2).into(holder.pictureItem2)
            if(post.picture_3!=null)Glide.with(MyApplication.context).load(post.picture_3).into(holder.pictureItem3)
            if(post.picture_4!=null){
                holder.pictureLayoutSecond.visibility=View.VISIBLE//第二个布局显示
                Glide.with(MyApplication.context).load(post.picture_4).into(holder.pictureItem4)
                if(post.picture_5!=null)Glide.with(MyApplication.context).load(post.picture_5).into(holder.pictureItem5)
                if(post.picture_6!=null)Glide.with(MyApplication.context).load(post.picture_6).into(holder.pictureItem6)
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
            post.goods++/* 待修改，应设置为每个用户只生效1次*/
            holder.agreedIcon.setBackgroundResource(
                R.drawable.dianzan_fill
            )
            holder.dianzan.text="${post.goods}"
        }
        holder.agreedIcon.setOnClickListener{
            post.goods++/* 待修改，应设置为每个用户只生效1次*/
            holder.agreedIcon.setBackgroundResource(
                R.drawable.dianzan_fill
            )
            holder.dianzan.text="${post.goods}"
        }

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
    }

    override fun getItemCount()=postList.size

    private fun clickPicture(post: PostB, i:Int){
        val intent = Intent(MyApplication.context, PictureShowActivity::class.java)
        intent.putExtra("post_data", post)
        intent.putExtra("show_position",i)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        MyApplication.context.startActivity(intent)
    }

}