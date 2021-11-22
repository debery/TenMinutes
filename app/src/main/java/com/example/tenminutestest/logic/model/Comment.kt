package com.example.tenminutestest.logic.model


import java.io.Serializable


//回复id、用户名、用户头像图片的id、评论内容、楼层数
class Comment(val comment_id:Int, val nickname:String, val avatar:String,
              val content:String, val floor:Int,val post_id: Int,val time:String):Serializable

class CommentUp(val post_id:Int,val content: String,val nickname: String)

class CommentResponse(val success:Boolean,val code:Int,val items:List<Comment>)