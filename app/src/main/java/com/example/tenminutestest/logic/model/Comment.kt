package com.example.tenminutestest.logic.model


import java.io.Serializable


//回复id、用户名、用户头像图片的id、评论内容、楼层数
class Comment(val comment_id:Int, val nickname:String, val avatar:String,
              val content:String, val floor:Int,val post_id: Int,val time:String):Serializable

class CommentBack(val userid: String,val post_id: Int,val comment_id: Int,
                  val content: String,val goods:Int,val time:String)

class AddCommentRequire(val userid:String, val content: String, val post_id: String,val tablename:String)

class AddCommentResponse(val success:Boolean,val message:String,val code:String,val items:CommentBack)

class GetCommentsRequire(val post_id: Int,val tablename:String)

class CommentResponse(val success:Boolean,val message:String,val code:String,val items:List<Comment>)
