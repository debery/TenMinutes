package com.example.tenminutestest2.ui.other.PostDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tenminutestest2.R
import com.example.tenminutestest2.logic.model.Reply

class ReplyAdapter(val replyList: List<Reply>):RecyclerView.Adapter<ReplyAdapter.ViewHolder>() {

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val image:ImageView=view.findViewById(R.id.replyImage)
        val name:TextView=view.findViewById(R.id.replyUserName)
        val floor:TextView=view.findViewById(R.id.replyFloor)
        val content:TextView=view.findViewById(R.id.replyContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_reply,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reply=replyList[position]
        holder.image.setImageResource(reply.userHeadId)
        holder.name.text=reply.userName
        holder.content.text=reply.content
    }

    override fun getItemCount()=replyList.size

}