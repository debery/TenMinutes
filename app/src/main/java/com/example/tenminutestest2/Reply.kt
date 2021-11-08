package com.example.tenminutestest2

import java.io.Serializable

//回复id、用户名、用户头像图片的id、评论内容、楼层数
class Reply (val id:Int,val userName:String,val userHeadId:Int,val content:String,val floor:Int):Serializable