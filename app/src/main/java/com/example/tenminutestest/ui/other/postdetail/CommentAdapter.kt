package com.example.tenminutestest.ui.other.postdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tenminutestest.R
import com.example.tenminutestest.logic.model.Comment

class CommentAdapter(private val commentList: List<Comment>):RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val image:ImageView=view.findViewById(R.id.replyImage)
        val name:TextView=view.findViewById(R.id.replyUserName)
        val floor:TextView=view.findViewById(R.id.replyFloor)
        val content:TextView=view.findViewById(R.id.replyContent)
        val time:TextView=view.findViewById(R.id.replyTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_reply,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment=commentList[position]
        val floor=position+1
        //holder.image.setImageResource(comment.userHeadId)
        holder.name.text=comment.nickname
        holder.content.text=comment.content
        holder.floor.text=floor.toString()+"楼"
        holder.time.text=comment.time
    }

    override fun getItemCount()=commentList.size

}