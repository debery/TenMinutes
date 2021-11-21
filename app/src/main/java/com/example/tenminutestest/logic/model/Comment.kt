package com.example.tenminutestest.logic.model


import java.io.Serializable


//回复id、用户名、用户头像图片的id、评论内容、楼层数
class Comment(val comment_id:Int, val nickname:String, val userHeadId:Int, val content:String, val floor:Int):Serializable

class CommentResponse(val items:List<Comment>)