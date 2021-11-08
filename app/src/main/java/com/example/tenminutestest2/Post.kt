package com.example.tenminutestest2

import java.io.Serializable

//帖子id、用户名称、用户头像的图片的id，帖子标题、评论内容、六个图片的id（默认无）、点赞数、回复数、回复的集合
class Post (val id:Int,val userName:String,val userHeadId:Int,val postTitle:String,val commentText:String,
            val commentImage1:Int=0,val commentImage2: Int=0,val commentImage3: Int=0,
            val commentImage4:Int=0,val commentImage5: Int=0,val commentImage6: Int=0,
            var agreedCount:Int=0,var replyCount:Int=0,var replyList: ArrayList<Reply>?=null):Serializable