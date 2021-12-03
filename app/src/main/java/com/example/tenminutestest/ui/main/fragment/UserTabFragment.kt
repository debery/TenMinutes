package com.example.tenminutestest.ui.main.fragment

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.example.tenminutestest.util.BitmapUtil
import com.example.tenminutestest.MyApplication
import com.example.tenminutestest.R
import com.example.tenminutestest.util.User_IO
import com.example.tenminutestest.logic.ContentUriUtil
import com.example.tenminutestest.logic.model.FileResponse
import com.example.tenminutestest.logic.model.PostResponse
import com.example.tenminutestest.logic.model.UserData
import com.example.tenminutestest.logic.network.FileService
import com.example.tenminutestest.logic.network.ServiceCreator
import com.example.tenminutestest.logic.network.UserService
import com.example.tenminutestest.ui.main.MainActivity
import com.example.tenminutestest.ui.other.Config
import com.example.tenminutestest.ui.other.Punch_pt
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UserTabFragment:Fragment() {
    //配置两ListView
    var first: ListView? = null
    var second: ListView? = null
    private val firsticon: ImageView? = null
    private val secondicon: ImageView? = null //分别为两个ListView所用，下同
    private val firsttitle: TextView? = null
    private val secondtitle: TextView? = null
    val firsticons = listOf(R.drawable.helper, R.drawable.renzheng, R.drawable.er_wei_ma, R.drawable.my_level)
    val secondicons = listOf(R.drawable.my_class, R.drawable.helper)
    val firsttitles = listOf("每日打卡", "身份认证", "我的二维码", "我的等级")
    val secondtitles = listOf("我的行政班级", "帮助与反馈中心")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mine,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val imageView:ImageView=view?.findViewById(R.id.face)!!
        val userList= User_IO.get_userinfos(MyApplication.context)

        if(userList[3]!=null){
            Glide.with(activity).load(userList[3]).into(imageView)
            Log.d("userlist[3]","${userList[3]}")
        }

        first = view?.findViewById(R.id.Firstlist)
        second = view?.findViewById(R.id.Secondlist)
        val fir = firstMineAdapter(firsticons,firsttitles)
        first!!.adapter = fir
        val SMA = SecondMineAdapter(secondicons,secondtitles)
        second!!.adapter = SMA

        first!!.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                val intent = Intent(activity,Punch_pt::class.java)
                startActivity(intent)
            }
        }
        val config:Button=view?.findViewById(R.id.config)!!
        config.setOnClickListener {
            val intent = Intent(activity, Config::class.java)
            startActivity(intent)
        }
        val nickname:TextView=view?.findViewById(R.id.user_nickname)!!
        val id:TextView=view?.findViewById(R.id.user_id)!!
        val user= User_IO.get_userinfos(activity)
        if(user!=null){
            nickname.text=user[2]
            id.text=user[0]
        }
        //上传头像
        imageView.setOnClickListener {
            showAvatarWindow()
        }
    }
    private fun showAvatarWindow(){

        val contentView= LayoutInflater.from(activity).inflate(R.layout.pop_avatar,null)
        val popupWindow= PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.isFocusable=true
        popupWindow.setBackgroundDrawable(ColorDrawable(0x800000))
        popupWindow.isOutsideTouchable=true
        popupWindow.isTouchable=true

        popupWindow.showAtLocation(contentView, Gravity.BOTTOM,0,0)
        //弹出窗口后将背景虚化
        backgroundAlpha(0.5f,activity)
        popupWindow.setOnDismissListener {
            popupWindow.dismiss()
            backgroundAlpha(1f,activity)
        }

        //获取popupWindow里的控件之前，使用view保存R.layout.pop生成的contentView
        val view: View =popupWindow.contentView
        val btnRefresh:Button=view.findViewById(R.id.btnRefresh)
        val btnCancel:Button=view.findViewById(R.id.btnCancel)

        btnCancel.setOnClickListener {
            popupWindow.dismiss()
            backgroundAlpha(1f,activity)
        }

        btnRefresh.setOnClickListener {
            popupWindow.dismiss()
            backgroundAlpha(1f,activity)
            val intent=Intent( Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type="image/*"
            startActivityForResult(intent,1)

        }
    }
    private fun backgroundAlpha(alphaVal:Float,activity: FragmentActivity?){
        val act: MainActivity =activity as MainActivity
        act.backgroundAlpha(alphaVal)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1->{
                if(data?.data!=null){
                    //上传文件且修改头像
                    val user= User_IO.get_userinfos(activity)
                    val uid=user[0]
                    //获取图片真实地址
                    val imageFile=File(ContentUriUtil().getPath(MyApplication.context,data.data!!)!!)
                    //压缩图片
                    val file=File(BitmapUtil.compressImage(imageFile.path))

                    val fileService= ServiceCreator.create2(FileService::class.java)//文件接口动态代理
                    //创建接口需要的参数
                    val requestFile= file.asRequestBody("image/jpg".toMediaTypeOrNull())
                    val body= MultipartBody.Part.createFormData("file","$uid"+file.name,requestFile)
                    fileService.uploadFile(body).enqueue(object :Callback<FileResponse>{
                        override fun onResponse(
                            call: Call<FileResponse>,
                            response: Response<FileResponse>
                        ) {
                            //上传成功，修改控件与本地文件
                            if(response.isSuccessful){
                                Toast.makeText(activity,"上传头像成功",Toast.LENGTH_SHORT).show()
                                val avatar:ImageView=view?.findViewById(R.id.face)!!
                                val path=response.body()?.fileDownloadUri
                                Glide.with(activity).load(path).into(avatar)
                                User_IO.saveAvatar(path,MyApplication.context)
                                //通知后台用户数据更改
                                val userService= ServiceCreator.create(UserService::class.java)//用户数据接口动态代理
                                val userUp=UserData(uid,user_avatar = path)
                                //通过PostResponse接收一般返回数据
                                userService.updateAvatar(userUp).enqueue(object :Callback<PostResponse>{
                                    override fun onResponse(
                                        call: Call<PostResponse>,
                                        response: Response<PostResponse>
                                    ) {
                                        Log.d("updateUserAvatar",response.isSuccessful.toString())
                                    }

                                    override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                                        t.printStackTrace()
                                    }

                                })
                            }



                        }

                        override fun onFailure(call: Call<FileResponse>, t: Throwable) {
                            t.printStackTrace()
                        }

                    })
                }
            }
        }

    }

    //设置透明度的代码


//    fun config(view: View?) {
//        val intent = Intent(activity, Config::class.java)
//        startActivity(intent)
//    }

    private class firstMineAdapter(val list:List<Int>,val listSecond:List<String>) : BaseAdapter() //"我的"页面第一个适配器
    {
        override fun getCount(): Int {
            return list.size
        }

        override fun getItem(position: Int): Any {
            return list.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            var convertView = convertView
            var fmine: FMineViewHolder? = null
            if (convertView == null) {
                convertView = View.inflate(MyApplication.context, R.layout.itemlaout, null)
                fmine = FMineViewHolder()
                fmine.firsticon = convertView.findViewById(R.id.icon)
                fmine.firsttitle = convertView.findViewById(R.id.identity)
                convertView.tag = fmine
            } else {
                fmine = convertView.tag as FMineViewHolder
            }
            fmine.firsticon!!.setBackgroundResource(list.get(position))
            fmine.firsttitle?.setText(listSecond.get(position))
            return convertView
        }
    }

    internal class FMineViewHolder {
        var firsticon: ImageView? = null
        var firsttitle: TextView? = null
    }


    private class SecondMineAdapter(val list:List<Int>,val list2:List<String>) : BaseAdapter() //"我的"页面第二个适配器
    {
        override fun getCount(): Int {
            return list.size
        }

        override fun getItem(position: Int): Any {
            return list.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            var convertView = convertView
            var smine: SMineViewHolder? = null
            if (convertView == null) {
                convertView = View.inflate(MyApplication.context, R.layout.secmine_item, null)
                smine = SMineViewHolder()
                smine.secondticon = convertView.findViewById(R.id.icon2)
                smine.secondtitle = convertView.findViewById(R.id.identity2)
                convertView.tag = smine
            } else {
                smine = convertView.tag as SMineViewHolder
            }
            smine.secondticon!!.setBackgroundResource(list.get(position))
            smine.secondtitle?.setText(list2.get(position))
            return convertView
        }
    }

    internal class SMineViewHolder {
        var secondticon: ImageView? = null
        var secondtitle: TextView? = null
    }
}