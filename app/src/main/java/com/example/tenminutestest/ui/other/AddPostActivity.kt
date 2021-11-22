package com.example.tenminutestest.ui.other

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import com.example.tenminutestest.BaseActivity
import com.example.tenminutestest.R
import com.example.tenminutestest.logic.model.PostResponse
import com.example.tenminutestest.logic.network.PostService
import com.example.tenminutestest.logic.network.ServiceCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

import com.example.tenminutestest.MyApplication
import com.example.tenminutestest.logic.ContentUriUtil
import com.example.tenminutestest.logic.model.FileResponse
import com.example.tenminutestest.logic.model.PostUp
import com.example.tenminutestest.logic.network.FileService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import com.example.tenminutestest.logic.RequestInRun
import okhttp3.RequestBody.Companion.asRequestBody
import androidx.core.app.ActivityCompat
import com.example.tenminutestest.User_IO


class AddPostActivity : BaseActivity() {

    private var image1=File("")
    private var image2=File("")
    private var image3=File("")
    private var image4=File("")
    private var image5=File("")
    private var image6=File("")
    //这两个用于后面判定帖子的图片执行代码
    private val imageList=ArrayList<File>()
    private var imageCount=0

    private var uriFile: String? =null


    private val teaching=1
    private val arts=2
    private val sport=3
    private var place=arts//默认艺术类


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        val permission = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED)
            Toast.makeText(this,"上传图片到网络需要有存储权限",Toast.LENGTH_LONG).show()
        RequestInRun().verifyStoragePermissions(this)

        val btnFinish:ImageView=findViewById(R.id.btnAddPostActivityFinish)
        val choosePlace:LinearLayout=findViewById(R.id.addPostActivityPlaceLayout)
        val picture1:ImageView=findViewById(R.id.picture1)
        val deliver:Button=findViewById(R.id.deliver)
        val printTitle:EditText=findViewById(R.id.printTitle)
        val printContent:EditText=findViewById(R.id.printContent)

        btnFinish.setOnClickListener {
            finish()
        }
        picture1.setOnClickListener {
            showPicturePopWindow()
        }
        choosePlace.setOnClickListener {
            showPlacePopWindow()
        }
        deliver.setOnClickListener {
            if(printTitle.text.length<4){
                Toast.makeText(this,"标题长度小于四个字符",Toast.LENGTH_SHORT).show()

            }else if(printTitle.text.length>30){
                Toast.makeText(this,"标题长度不可大于三十个字",Toast.LENGTH_SHORT).show()
            }else{
                postAdd()
            }

        }
    }

    private fun showPlacePopWindow(){
        val contentView= LayoutInflater.from(this).inflate(R.layout.pop_select_place,null)
        val popupWindow= PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.isFocusable=true
        popupWindow.setBackgroundDrawable(ColorDrawable(0x800000))
        popupWindow.isOutsideTouchable=true
        popupWindow.isTouchable=true

        popupWindow.showAtLocation(contentView,Gravity.CENTER,0,0)
        backgroundAlpha(0.5f)
        popupWindow.setOnDismissListener {
            popupWindow.dismiss()
            backgroundAlpha(1f)
        }

        //板块选择
        val btnTeach:Button=contentView.findViewById(R.id.btnTeach)
        val btnDraw:Button=contentView.findViewById(R.id.btnDraw)
        val btnSport:Button=contentView.findViewById(R.id.btnSport)
        val btnCancel:Button=contentView.findViewById(R.id.btnCancel)

        btnCancel.setOnClickListener {
            popupWindow.dismiss()
            backgroundAlpha(1f)
        }
        btnTeach.setOnClickListener {
            place=teaching
            Toast.makeText(this,"选择了 教技 板块",Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
            backgroundAlpha(1f)
        }
        btnDraw.setOnClickListener {
            place=arts
            Toast.makeText(this,"选择了 艺术 板块",Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
            backgroundAlpha(1f)
        }
        btnSport.setOnClickListener {
            place=sport
            Toast.makeText(this,"选择了 体育 板块",Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
            backgroundAlpha(1f)
        }

    }

    //弹窗的控制代码
    private fun showPicturePopWindow(){

        val contentView= LayoutInflater.from(this).inflate(R.layout.pop_add_picture,null)
        val popupWindow= PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.isFocusable=true
        popupWindow.setBackgroundDrawable(ColorDrawable(0x800000))
        popupWindow.isOutsideTouchable=true
        popupWindow.isTouchable=true

        popupWindow.showAtLocation(contentView, Gravity.BOTTOM,0,0)
        //弹出窗口后将背景虚化
        backgroundAlpha(0.5f)
        popupWindow.setOnDismissListener {
            popupWindow.dismiss()
            backgroundAlpha(1f)
        }

        //获取popupWindow里的控件之前，使用view保存R.layout.pop生成的contentView
        val view: View =popupWindow.contentView
        val btnCamera:Button=view.findViewById(R.id.btnCamera)
        val btnAlbum:Button=view.findViewById(R.id.btnAlbum)
        val btnCancel:Button=view.findViewById(R.id.btnCancel)

        btnCancel.setOnClickListener {
            popupWindow.dismiss()
            backgroundAlpha(1f)
        }
        btnCamera.setOnClickListener {
            Toast.makeText(this,"暂不支持",Toast.LENGTH_SHORT).show()
        }
        btnAlbum.setOnClickListener {
            popupWindow.dismiss()
            val intent=Intent( Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type="image/*"
            startActivityForResult(intent,1)
        }

    }
    //设置透明度的代码
    private fun backgroundAlpha(alphaVal:Float){
        val alp: WindowManager.LayoutParams = this.window.attributes
        alp.alpha=alphaVal
        this.window.attributes=alp
        this.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val picture1:ImageView=findViewById(R.id.picture1)
        val picture2:ImageView=findViewById(R.id.picture2)
        val picture3:ImageView=findViewById(R.id.picture3)
        val picture4:ImageView=findViewById(R.id.picture4)
        val picture5:ImageView=findViewById(R.id.picture5)
        val picture6:ImageView=findViewById(R.id.picture6)


        when(requestCode){
            1->{
                if(resultCode==Activity.RESULT_OK && data!=null){
                    /*
                    data.data?.let {
                        val bitmap=contentResolver.openFileDescriptor(it,"r")?.use {
                            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
                        }
                */
                    /*
                    outputImage = File(externalCacheDir,"output_image.jpg")
                    if(outputImage.exists()){
                        outputImage.delete()
                    }
                    outputImage.createNewFile()
                    imageUri = if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
                        FileProvider.getUriForFile(this,"com.example.cameraalbumtest.fileprovider",outputImage)
                    }else{
                        Uri.fromFile(outputImage)
                    }
                    */

                    RequestInRun().verifyStoragePermissions(this)
                    image1=File(ContentUriUtil().getPath(MyApplication.context,data.data!!)!!)
                    Log.d("wuhu",
                        Uri.fromFile(File(ContentUriUtil()
                            .getPath(MyApplication.context,data.data!!)!!)).toString())
                    imageClick(data.data!!,picture1,picture2,2)
                    imageCount++
                }
            }
            2->{
                if(resultCode==Activity.RESULT_OK && data!=null){
                    image2=File(ContentUriUtil().getPath(MyApplication.context,data.data!!)!!)
                    imageClick(data.data!!,picture2,picture3,3)
                    imageCount++
                }
            }
            3->{
                if(resultCode==Activity.RESULT_OK && data!=null){
                    val layout:LinearLayout=findViewById(R.id.addPostActivityPictureLayout)
                    layout.visibility=View.VISIBLE
                    image3=File(ContentUriUtil().getPath(MyApplication.context,data.data!!)!!)
                    imageClick(data.data!!,picture3,picture4,4)
                    imageCount++
                }
            }
            4->{
                if(resultCode==Activity.RESULT_OK && data!=null){

                    image4=File(ContentUriUtil().getPath(MyApplication.context,data.data!!)!!)
                    imageClick(data.data!!,picture4,picture5,5)
                    imageCount++
                }
            }
            5->{
                if(resultCode==Activity.RESULT_OK && data!=null){
                    image5=File(ContentUriUtil().getPath(MyApplication.context,data.data!!)!!)
                    imageClick(data.data!!,picture5,picture6,6)
                    imageCount++
                }
            }
            6->{
                if(resultCode==Activity.RESULT_OK && data!=null){
                    image6=File(ContentUriUtil().getPath(MyApplication.context,data.data!!)!!)
                    picture6.setImageURI(data.data)
                    picture6.setOnClickListener {  }
                    imageCount++
                }
            }
        }
    }

    private fun imageClick(uri: Uri, now:ImageView, next:ImageView,nextCode: Int){
        now.setImageURI(uri)
        now.setOnClickListener {  }
        next.visibility=View.VISIBLE
        next.setOnClickListener {
            val intent=Intent( Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type="image/*"
            startActivityForResult(intent,nextCode)
        }
    }



    private fun postAdd(){
        val printTitle:EditText=findViewById(R.id.printTitle)
        val printContent:EditText=findViewById(R.id.printContent)
        var imageMethod=false


        //形成帖子
        val user=User_IO.get_userinfos(this)
        val nickname=user[2]
        if(nickname!=null){
            val post= PostUp(nickname,
                post_title = "${printTitle.text}",content = "${printContent.text}")
            //如有图片，上传并将服务器返回的图片地址添加到帖子子里
            if(image1!=File("")){
                imageMethod=uploadImageAndPost(image1,post)
                uriFile?.let { Log.d("after upload,uriFile", it) }
                Log.d("after upload,picture_1",post.picture_1.toString())
            }
            if(image2!=File("")){
                uploadImageAndPost(image2,post)
            }
            if(image3!=File("")){
                uploadImageAndPost(image3,post)
            }
            if(image4!=File("")){
                uploadImageAndPost(image4,post)
            }
            if(image5!=File("")){
                uploadImageAndPost(image5,post)
            }
            if(image6!=File("")){
                uploadImageAndPost(image6,post)
            }
            //根据板块不同，调用不同的add接口
            val postService=ServiceCreator.create(PostService::class.java)
            if(!imageMethod){
                when(place){
                    teaching->{
                        postService.addPostOfTeaching(post).enqueue(object : Callback<PostResponse> {
                            override fun onResponse(
                                call: Call<PostResponse>,
                                response: Response<PostResponse>
                            ) {
                                val data:PostResponse?=response.body()
                                Toast.makeText(MyApplication.context,"发送成功",Toast.LENGTH_SHORT).show()
                                Log.d("AddPostActivity", data?.code.toString())
                                finish()
                            }

                            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                                t.printStackTrace()
                                Toast.makeText(MyApplication.context,"发送失败",Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                    arts->{
                        postService.addPostOfArts(post).enqueue(object : Callback<PostResponse> {
                            override fun onResponse(
                                call: Call<PostResponse>,
                                response: Response<PostResponse>
                            ) {
                                val data:PostResponse?=response.body()
                                Toast.makeText(MyApplication.context,"发送成功",Toast.LENGTH_SHORT).show()
                                Log.d("AddPostActivity", data?.code.toString())
                                finish()
                            }

                            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                                t.printStackTrace()
                                Toast.makeText(MyApplication.context,"发送失败",Toast.LENGTH_SHORT).show()
                            }
                        })

                    }
                    sport->{
                        postService.addPostOfSport(post).enqueue(object : Callback<PostResponse> {
                            override fun onResponse(
                                call: Call<PostResponse>,
                                response: Response<PostResponse>
                            ) {
                                val data:PostResponse?=response.body()
                                Toast.makeText(MyApplication.context,"发送成功",Toast.LENGTH_SHORT).show()
                                Log.d("AddPostActivity", data?.code.toString())
                                finish()
                            }

                            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                                t.printStackTrace()
                                Toast.makeText(MyApplication.context,"发送失败",Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                }
            }
        }else{
            Toast.makeText(this,"账号不存在，请先登录",Toast.LENGTH_SHORT).show()
        }


    }
    private fun uploadImageAndPost(image:File,post: PostUp):Boolean{

        val fileService=ServiceCreator.create2(FileService::class.java)
        val postService=ServiceCreator.create(PostService::class.java)//动态代理
        //创建接口需要的参数
        val requestFile= image.asRequestBody("image/jpg".toMediaTypeOrNull())
        val body=MultipartBody.Part.createFormData("file",image.name,requestFile)
        fileService.uploadFile(body).enqueue(object :Callback<FileResponse>{
            override fun onResponse(call: Call<FileResponse>, response: Response<FileResponse>) {
                uriFile= response.body()?.fileDownloadUri.toString()
                if(image==image1) {
                    post.picture_1=uriFile
                }
                if(image==image2) {
                    post.picture_2=uriFile
                }
                if(image==image3) {
                    post.picture_3=uriFile
                }
                if(image==image4) {
                    post.picture_4=uriFile
                }
                if(image==image5) {
                    post.picture_5=uriFile
                }
                if(image==image6) {
                    post.picture_6=uriFile
                }
                imageList.add(image)
                Log.d("upload", uriFile!!)


                if(imageList.size==imageCount)//只执行一次addPost
                when(place){
                    teaching->{
                        postService.addPostOfTeaching(post).enqueue(object : Callback<PostResponse> {
                            override fun onResponse(
                                call: Call<PostResponse>,
                                response: Response<PostResponse>
                            ) {
                                val data:PostResponse?=response.body()
                                Toast.makeText(MyApplication.context,"发送成功",Toast.LENGTH_SHORT).show()
                                Log.d("AddPostActivity", data?.code.toString())
                                finish()
                            }

                            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                                t.printStackTrace()
                                Toast.makeText(MyApplication.context,"发送失败",Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                    arts->{
                        postService.addPostOfArts(post).enqueue(object : Callback<PostResponse> {
                            override fun onResponse(
                                call: Call<PostResponse>,
                                response: Response<PostResponse>
                            ) {
                                val data:PostResponse?=response.body()
                                Toast.makeText(MyApplication.context,"发送成功",Toast.LENGTH_SHORT).show()
                                Log.d("AddPostActivity", data?.code.toString())
                                finish()
                            }

                            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                                t.printStackTrace()
                                Toast.makeText(MyApplication.context,"发送失败",Toast.LENGTH_SHORT).show()
                            }
                        })

                    }
                    sport->{
                        postService.addPostOfSport(post).enqueue(object : Callback<PostResponse> {
                            override fun onResponse(
                                call: Call<PostResponse>,
                                response: Response<PostResponse>
                            ) {
                                val data:PostResponse?=response.body()
                                Toast.makeText(MyApplication.context,"发送成功",Toast.LENGTH_SHORT).show()
                                Log.d("AddPostActivity", data?.code.toString())
                                finish()
                            }

                            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                                t.printStackTrace()
                                Toast.makeText(MyApplication.context,"发送失败",Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                }
            }

            override fun onFailure(call: Call<FileResponse>, t: Throwable) {
                t.printStackTrace()
                Log.d("upload","失败")
            }
        })
        return true

    }

}