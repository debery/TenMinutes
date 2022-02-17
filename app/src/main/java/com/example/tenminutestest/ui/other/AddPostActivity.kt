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
import com.example.tenminutestest.logic.network.PostService
import com.example.tenminutestest.logic.network.ServiceCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import com.example.tenminutestest.logic.ContentUriUtil
import com.example.tenminutestest.logic.model.FileResponse
import com.example.tenminutestest.logic.model.AddPostRequire
import com.example.tenminutestest.logic.network.FileService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import com.example.tenminutestest.logic.RequestInRun
import okhttp3.RequestBody.Companion.asRequestBody
import androidx.core.app.ActivityCompat
import com.example.tenminutestest.*
import com.example.tenminutestest.logic.model.UniveResponse
import com.example.tenminutestest.util.BitmapUtil
import com.example.tenminutestest.util.User_IO


class AddPostActivity : BaseActivity() {

    private var image1=File("")
    private var image2=File("")
    private var image3=File("")
    private var image4=File("")
    private var image5=File("")
    private var image6=File("")
    private var video=File("")
    //这两个用于后面判定帖子的图片执行代码
    private val imageList=ArrayList<File>()
    private var imageCount=0

    private var uriFile: String? =null


    private val teaching=1
    private val arts=2
    private val sport=3
    private var plank=0


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
        val location:LinearLayout=findViewById(R.id.locationLayout)
        val picture1:ImageView=findViewById(R.id.picture1)
        val deliver:Button=findViewById(R.id.deliver)
        val printTitle:EditText=findViewById(R.id.printTitle)
        val printContent:EditText=findViewById(R.id.printContent)


        plank = intent.getIntExtra("fragment",1)
        Log.d("addActivity",intent.getIntExtra("fragment",1).toString())//0

        when(plank){
            0->Toast.makeText(this,"0",Toast.LENGTH_SHORT).show()
            1->Toast.makeText(this,"教技",Toast.LENGTH_SHORT).show()
            2->Toast.makeText(this,"艺术",Toast.LENGTH_SHORT).show()
            3->Toast.makeText(this,"体育",Toast.LENGTH_SHORT).show()
        }

        showPlankText()
        btnFinish.setOnClickListener {
            finish()
        }
        picture1.setOnClickListener {
            showPicturePopWindow()
        }
        choosePlace.setOnClickListener {
            showPlankPopWindow()
        }
        location.setOnClickListener {
            Toast.makeText(this,"如果我没猜错，你的位置现在在岭南师范学院",Toast.LENGTH_LONG).show()
        }
        deliver.setOnClickListener {
            if(printTitle.text.toString().isNotEmpty()){
                if(printTitle.text.toString().length<30){
                    postAdd()
                }else{
                    Toast.makeText(this,"标题不可大于30字",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"标题不可为空",Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun showPlankText(){
        val show:TextView=findViewById(R.id.plankShow)
        when(plank){
            teaching-> show.text="教技"
            arts-> show.text="艺术"
            sport-> show.text="体育"
        }
    }

    private fun showPlankPopWindow(){
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
            plank=teaching
            Toast.makeText(this,"选择了 教技 板块",Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
            backgroundAlpha(1f)
            showPlankText()
        }
        btnDraw.setOnClickListener {
            plank=arts
            Toast.makeText(this,"选择了 艺术 板块",Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
            backgroundAlpha(1f)
            showPlankText()
        }
        btnSport.setOnClickListener {
            plank=sport
            Toast.makeText(this,"选择了 体育 板块",Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
            backgroundAlpha(1f)
            showPlankText()
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
        val btnVideo:Button=view.findViewById(R.id.btnVideo)

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
        btnVideo.setOnClickListener {
            popupWindow.dismiss()
            RequestInRun().verifyStoragePermissions(this)
            val intent=Intent( Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type="video/*"
            startActivityForResult(intent,7)
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

        val layout1:LinearLayout=findViewById(R.id.pictureLayout1)
        val layout2:LinearLayout=findViewById(R.id.pictureLayout2)
        val picture1:ImageView=findViewById(R.id.picture1)
        val picture2:ImageView=findViewById(R.id.picture2)
        val picture3:ImageView=findViewById(R.id.picture3)
        val picture4:ImageView=findViewById(R.id.picture4)
        val picture5:ImageView=findViewById(R.id.picture5)
        val picture6:ImageView=findViewById(R.id.picture6)
        val videoView:VideoView=findViewById(R.id.video)


        when(requestCode){
            1->{
                if(resultCode==Activity.RESULT_OK && data!=null){
                    RequestInRun().verifyStoragePermissions(this)
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
                    //获取图片真实地址
                    val imagePath=File(ContentUriUtil().getPath(MyApplication.context,data.data!!)!!).path
                    Log.d("before compress",File(ContentUriUtil().getPath(MyApplication.context,data.data!!)!!).length().toString())
                    //压缩图片，并保存路径
                    image1=File(BitmapUtil.compressImage(imagePath))
                    Log.d("after compress",image1.length().toString())

                    imageClick(data.data!!,picture1,picture2,2)
                    imageCount++//用于上传时计数，有多少张图调用多少次上传接口
                }
            }
            2->{
                if(resultCode==Activity.RESULT_OK && data!=null){
                    val imagePath=File(ContentUriUtil().getPath(MyApplication.context,data.data!!)!!).path
                    image2=File(BitmapUtil.compressImage(imagePath))
                    imageClick(data.data!!,picture2,picture3,3)
                    imageCount++
                }
            }
            3->{
                if(resultCode==Activity.RESULT_OK && data!=null){

                    layout2.visibility=View.VISIBLE
                    val imagePath=File(ContentUriUtil().getPath(MyApplication.context,data.data!!)!!).path
                    image3=File(BitmapUtil.compressImage(imagePath))
                    imageClick(data.data!!,picture3,picture4,4)
                    imageCount++
                }
            }
            4->{
                if(resultCode==Activity.RESULT_OK && data!=null){
                    val imagePath=File(ContentUriUtil().getPath(MyApplication.context,data.data!!)!!).path
                    image4=File(BitmapUtil.compressImage(imagePath))
                    imageClick(data.data!!,picture4,picture5,5)
                    imageCount++
                }
            }
            5->{
                if(resultCode==Activity.RESULT_OK && data!=null){
                    val imagePath=File(ContentUriUtil().getPath(MyApplication.context,data.data!!)!!).path
                    image5=File(BitmapUtil.compressImage(imagePath))
                    imageClick(data.data!!,picture5,picture6,6)
                    imageCount++
                }
            }
            6->{
                if(resultCode==Activity.RESULT_OK && data!=null){
                    val imagePath=File(ContentUriUtil().getPath(MyApplication.context,data.data!!)!!).path
                    image6=File(BitmapUtil.compressImage(imagePath))

                    picture6.setImageURI(data.data)
                    picture6.setOnClickListener {  }
                    imageCount++
                }
            }
            7->{
                if(resultCode==Activity.RESULT_OK && data!=null){
                    val path=ContentUriUtil().getPath(MyApplication.context,data?.data!!)
                    video=File(path)
//                    val fileService=ServiceCreator.create2(FileService::class.java)
//                    val requestFile= video.asRequestBody("video/mp4".toMediaTypeOrNull())
//                    val body= MultipartBody.Part.createFormData("file",video.name,requestFile)
                    videoView.visibility=View.VISIBLE
                    videoView.setMediaController(MediaController(this))
                    videoView.setVideoURI(Uri.parse(path))
                    layout1.visibility=View.GONE
                    layout2.visibility=View.GONE

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
        var videoMethod=false

        //形成帖子
        val user= User_IO.get_userinfos(this)
        val userId:Int=user[0].toInt()
        val nickname=user[2]
        val avatar:String? = if(user.size>3&&user[3]!=""&&user[3]!="null"){
            user[3]
        }else{
            null
        }
        if(nickname!=null){
            val post= AddPostRequire(userId,
                "${printTitle.text}","${printContent.text}")
            //如有图片，上传并将服务器返回的图片地址添加到帖子子里
            if(image1.path!=""){
                imageMethod=uploadImageAndPost(image1,post)
                uriFile?.let { Log.d("after upload,uriFile", it) }
                Log.d("after upload,picture_1",post.picture_1.toString())
            }
            if(image2.path!=""){
                uploadImageAndPost(image2,post)
            }
            if(image3.path!=""){
                uploadImageAndPost(image3,post)
            }
            if(image4.path!=""){
                uploadImageAndPost(image4,post)
            }
            if(image5.path!=""){
                uploadImageAndPost(image5,post)
            }
            if(image6.path!=""){
                uploadImageAndPost(image6,post)
            }
            if(video.path!=""){
                uploadVideoAndPost(video,post)
                videoMethod=true
            }
            //根据板块不同，调用不同的add接口
            val postService=ServiceCreator.create(PostService::class.java)
            when(plank){
                teaching->post.tablename="post_teaching"
                arts->post.tablename="post_arts"
                sport->post.tablename="post_sports"
            }
            if(!imageMethod&&!videoMethod){

                postService.postPost(post).enqueue(object : Callback<UniveResponse> {
                    override fun onResponse(
                        call: Call<UniveResponse>,
                        response: Response<UniveResponse>
                    ) {
                        Toast.makeText(MyApplication.context,"发送成功",Toast.LENGTH_SHORT).show()
                        Log.d("AddPostTeaching", response.isSuccessful.toString())
                        val intent=Intent()
                        intent.putExtra("send",true)
                        setResult(1,intent)
                        finish()
                    }

                    override fun onFailure(call: Call<UniveResponse>, t: Throwable) {
                        t.printStackTrace()
                        Toast.makeText(MyApplication.context,"发送失败",Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }else{
            Toast.makeText(this,"账号不存在，请先登录",Toast.LENGTH_SHORT).show()
        }

    }

    //多图反复调用此方法，但只执行一次上传
    private fun uploadImageAndPost(image:File,post: AddPostRequire):Boolean{

        val fileService=ServiceCreator.create2(FileService::class.java)
        val postService=ServiceCreator.create(PostService::class.java)//动态代理
        //创建接口需要的参数
        val requestFile= image.asRequestBody("image/jpg".toMediaTypeOrNull())
        val body=MultipartBody.Part.createFormData("file",image.name,requestFile)
        fileService.uploadFile(body).enqueue(object :Callback<FileResponse>{
            override fun onResponse(call: Call<FileResponse>, response: Response<FileResponse>) {
                uriFile = response.body()?.fileDownloadUri.toString()
                if (image == image1) {
                    post.picture_1 = uriFile
                }
                if (image == image2) {
                    post.picture_2 = uriFile
                }
                if (image == image3) {
                    post.picture_3 = uriFile
                }
                if (image == image4) {
                    post.picture_4 = uriFile
                }
                if (image == image5) {
                    post.picture_5 = uriFile
                }
                if (image == image6) {
                    post.picture_6 = uriFile
                }
                imageList.add(image)
                Log.d("upload", uriFile!!)

                if (imageList.size == imageCount)//只执行一次addPost
                    postService.postPost(post).enqueue(object : Callback<UniveResponse> {
                        override fun onResponse(
                            call: Call<UniveResponse>,
                            response: Response<UniveResponse>
                        ) {
                            val data: UniveResponse? = response.body()
                            Toast.makeText(MyApplication.context, "发送成功", Toast.LENGTH_SHORT).show()
                            Log.d("AddPostTeaching", response.isSuccessful.toString())
                            finish()
                        }

                        override fun onFailure(call: Call<UniveResponse>, t: Throwable) {
                            t.printStackTrace()
                            Toast.makeText(MyApplication.context, "发送失败", Toast.LENGTH_SHORT).show()
                        }
                    })

            }
            override fun onFailure(call: Call<FileResponse>, t: Throwable) {
                t.printStackTrace()
                Log.d("upload","失败")
            }
        })
        return true

    }

    //上传视频的情况下直接上传帖子，因为不允许还上传图片
    private fun uploadVideoAndPost(video:File,post: AddPostRequire){
        //动态代理，上传视频使用fileSerVice，上传帖子使用postService
        val fileService=ServiceCreator.create2(FileService::class.java)
        val postService=ServiceCreator.create(PostService::class.java)
        //创建接口需要的参数
        val requestFile= video.asRequestBody("video/mp4".toMediaTypeOrNull())
        val body=MultipartBody.Part.createFormData("file",video.name,requestFile)
        fileService.uploadFile(body).enqueue(object :Callback<FileResponse>{
            override fun onResponse(call: Call<FileResponse>, response: Response<FileResponse>) {
                Log.d("AddActivity.uploadVideo",response.isSuccessful.toString())
                post.videos=response.body()?.fileDownloadUri
                postService.postPost(post).enqueue(object : Callback<UniveResponse> {
                    override fun onResponse(
                        call: Call<UniveResponse>, response: Response<UniveResponse>
                            ) {
                        Toast.makeText(MyApplication.context,"发送成功",Toast.LENGTH_SHORT).show()
                        Log.d("AddPostTeaching", response.isSuccessful.toString())
                        val intent=Intent()
                        intent.putExtra("send",true)
                        setResult(1,intent)
                        finish()
                    }

                    override fun onFailure(call: Call<UniveResponse>, t: Throwable) {
                        t.printStackTrace()
                        Toast.makeText(MyApplication.context,"发送失败",Toast.LENGTH_SHORT).show()
                    }
                })
            }

            override fun onFailure(call: Call<FileResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@AddPostActivity,"上传视频失败",Toast.LENGTH_SHORT).show()
            }

        })
    }
}