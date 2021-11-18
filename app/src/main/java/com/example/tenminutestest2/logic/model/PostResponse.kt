package com.example.tenminutestest2.logic.model

import java.io.Serializable

class ResponseFromServer (val success:Boolean,val message:String,val code:String,val items:List<PostB>)

class PostB(val post_id:Int, val nickname:String, val user_avatar:String?=null, val post_title:String, val content:String,
            var picture_1:String?=null, var picture_2:String?=null, var picture_3:String?=null,
            var picture_4:String?=null, var picture_5:String?=null, var picture_6:String?=null,
            val time:String?=null,
            var goods:Int=0, val replys:Int=0):Serializable

class PostUp(val nickname:String, val user_avatar:String?=null, val post_title:String, val content:String,
            var picture_1:String?=null, var picture_2:String?=null, var picture_3:String?=null,
            var picture_4:String?=null, var picture_5:String?=null, var picture_6:String?=null,
            val time:String?=null,
            var goods:Int=0, val replys:Int=0):Serializable

//帖子id、用户名称、用户头像的图片的id，帖子标题、评论内容、六个图片的id（默认无）、点赞数、回复数、回复的集合
//旧有测试类
class Post (val id:Int,val userName:String,val userHeadId:Int,val postTitle:String,val commentText:String,
            val commentImage1:Int=0,val commentImage2: Int=0,val commentImage3: Int=0,
            val commentImage4:Int=0,val commentImage5: Int=0,val commentImage6: Int=0,
            var agreedCount:Int=0,var replyCount:Int=0,var replyList: ArrayList<Reply>?=null):
    Serializable